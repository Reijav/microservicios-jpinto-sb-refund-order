# IMPLEMENTACIÓN DE MICROSERVICIOS EN UN SISTEMA DE REEMBOLSO DE GASTOS PARA EMPLEADOS

Se presenta el trabajo final del Curso de   **Microservicios con Spring Cloud Edición 2026-1**.

Implementando microservicios con Spring Cloud, para un sistema de reembolso de gastos para empleados. La arquitectura 

## Caso del Negocio
El proyecto se trata del flujo para el reeembolso de gastos para empleados. El proceso inicia desde el registro de facturas y valores por parte del empleado, donde se genera una orden de reembolso, la cual debe ser aprobada por parte del supervisor y culmina con el pago por parte del contador.


## Flujo del Proceso
1. **Registro de órden de reembolso.**
   - Ingreso de la orden de reembolso y sus detalles (facturas, notas de crédito) - **api-refund-v1.**
   - Consulta de datos de supervisor - **api-talent-human-v1**.
   - Notificación a supervisor para la aprobación de la orden de reembolso - **api-notification-v1**.
   - Actualización de orden de reembolso en mongodb a través de Eventos en Kafka (Productor: api-refund-v1, Subscriptor: api-refund-query-v1 ).

2. **Aprobación de órden de reembolso (Implementación de SAGA).**
   - Validación datos ingresados (Carga de datos: Empleado, Supervisor, Orden de Reembolso)
   - Verificación de que la orden de reembolso debe ser aprobada por usuario logeado .
   - Cambio de estado de la orden de reembolso (ORDERPAYGENERATED) - **api-refund-v1**.
   - Creación de registro de pago (CREATED) - **api-payment-v1**.
   - Notificación de orden de reembolso aprobada - **api-notification-v1**.
   - Actualización de orden de reembolso en mongodb a través de Eventos en Kafka (Productor: api-refund-v1,api-payment-v1 ; Subscriptor: api-refund-query-v1 ).

3. **Liquidación(Pago) del valor de la órden de reembolso (Implementación de SAGA).**
   - Validación datos ingresados (Carga de datos: Empleado, Supervisor, Orden de Reembolso, Pago).
   - Creación de asiento contable en base al pago de la orden de reembolso - **api-accounting-v1**.
   - Procesar pago (Cambio de estado a PROCESSED) - **api-payment-v1**.
   - Cambio de estado de la Orden de Pago a PAYED - **api-refund-v1**.
   - Notificación de liquidación realizada - **api-notification-v1**.
   - Actualización de orden de reembolso en mongodb a través de Eventos en Kafka (Productor: api-refund-v1, api-payment-v1, api-accounting-v1 ; Subscriptor: api-refund-query-v1 ).

4. **Rechazo de órden de reembolso.**
   - Validación existencia de orden de reembolso y datos requeridos para el rechazo.
   - Cambio de estado a REJECT - **api-refund-v1**.
   - Notificación de orden de reembolso rechazada - **api-notification-v1**.
   - Actualización de orden de reembolso en mongodb a través de Eventos en Kafka (Productor: api-refund-v1, Subscriptor: api-refund-query-v1 ).

## Microservicios Implementados.

- **api-accounting-v1**: Api de contabilidad para registrar las transacciones o asientos contables que se generan al realizar un pago de unan orden de reembolso.
- **api-auth-service-v1**: Api para la autenticación de usuarios, creación de tokens, validación de token.
- **api-eureka-server-v1**: Servidor de descubrimiento de servicios.
- **api-notification-v1**: Api que emula el envio de notificaciones según el registro de orden de reembolso.
- **api-payment-v1**: Api que registra los pagos a procesar y los procesados en el flujo de liquidación de gastos.
- **api-refund-orchestator-v1**: Api orquestador de los 3 principales flujos (creación, aprobación, rechazo, liquidación).
- **api-refund-query-v1**: Api que implementa la lectura de ordenes de reembolso y los listeners (Kafka) para la actualización del estado de la orden de reembolso.
- **api-refund-v1**: Api que permite el registro de las ordenes de reembolso, su cambio de estados.
- **api-talent-human-v1**: Api que permite ver la información de los empleados (emails, cuentas de ahorros, nombres, id de usuario).

## Diagrama arquitectura.

![Arquitectura](/Documentacion/Pantallas/arquitectura_microservicios_refund.png)

### Datos Técnicos (CheckList)
- Uso de Restclient dentro del orquestador para comunicación con el resto de servicios.
- Implementación de @CircuitBreaker **cbCreateOrderRefund** en api-refund-orchestator-v1:  com.jpinto.orchestator.services.OrderRefundService método **create**.
- Implementación de @CircuitBreaker **cbPayOrderRefund** en api-refund-orchestator-v1:  com.jpinto.orchestator.services.PaymentService método **process**.
- Implementación de @Retry **defaultRetry*** en api-refund-orchestator-v1:  com.jpinto.orchestator.services.NotificationService método **encolarEnvioHtmlMail**.
- Config Server (Opcional) : No implementado.
- Se configuró Eureka Client en cada micro servicio para el descubrimiento de microservicios desde el orquestador (application-dev.yml).

  ![Eureka Server](/Documentacion/Pantallas/eureka%20server.png)

- Bases de datos independientes: Cada microservicio tiene su propia base de datos:
  - **api-accounting-v1**:  accountingdb(accounts, transactions , transactions-line).
  - **api-auth-service-v1**:  authdb(users, users_roles).
  - **api-payment-v1**: paymentdb(payment).
  - **api-refund-v1**: refundb(refund_order, refund_bill).
  - **api-talent-human-v1**: talenthumandb(employee).
  - **api-refund-query**: MongoDb(order-read).

- Mensajerìa Kafka
  - **Consumer** (api-refund-query-v): consume todos los mensajes enviados a los topicos, para actualizar los datos en MongoDb de las ordenes de reembolsos.
  - **Producers**
    - api-refund-v1: OrderRefundCreatedProducer, OrderRefundApprovedProducer, OrderRefundRejectedProducter
    - api-payment-v1: CreatePaymentProducer, ProcessPaymentProducer.
    - api-accounting-v1: CreatedTransactionProducer, CancelTransactionProducer.

- Seguridad
  - Implementación de Spring Security (JWT, OAuth) con autenticación local authdb(users), usado en todos los api. 
    - Contraseñas guardadas en base de datos usando BCrypt (hashes).
  - Autorización de recursos por rol (**@PreAuthorize** implementado en api-refund-orchestator-v1)
  - Se agrega el id del usuario en el token (JWT), para validar si el supervisor es el asignado para aprobar o rechazar las ordenes de reembolso.
  
- Swagger
  - Documentación realizada en api-refund-orchestator-v1.
    - [Orquestador Swagger](http://localhost:51100/api-orchestator-refund/swagger-ui/index.html).
  - Api Gateway (Opcional): no implementado.
  - Dashboard Grafana: no implementado.
- Infraestructura
  - Dentro de la carpeta Docker, se encuentra el archivo docker-compose.yml, el cual permitirá el levantar toda la infraestructura, para que trabajen los microservicios en el caso de negocio presentado.
    - En terminarl usar el comando:
    
```terminarlinput
    docker network create ms-trabajo-final-jpinto
```
```terminarlinput
    docker compose up -d
```
  
## Instrucciones de Levantamiento de Infraestructura.

### Requisitos
- Para la infraestructura tener instalado Docker Desktop, docker compose en la máquina local.
- Tener instalado el IDE IntelliJ.
- Tener instalado la plataforma de desarrollo OpenJDK 25.
- Tener instalado la plataforma de gestión de APIs Postman

### Levantamiento del Sistema

1. Descargar y abrir proyecto en Intellij (Carpeta principal). Cargar proyectos Maven.


2. Para la infraestructura: ejecutar el siguiente comando, para crear red en docker.

    ```terminaloutput
    docker network create ms-trabajo-final-jpinto
    ```
3. Ingresar a la carpeta /docker por la terminal y Ejecutar el siguiente comando:

    ```terminarlinput
    docker compose up -d 
    ```
    En este momento verificar que todos los contenedores se encuentren levantados.

   ![Docker Contenedores](/Documentacion/Pantallas/Docker%20contenedores%20proyecto.png)

4. Importar archivo json de postman (./Documentacion/TRABAJO FINAL CURSO MICROSERVICIOS JAVIER PINTO.postman_collection.json)    

   ![Postman Proyecto](/Documentacion/Pantallas/Postman%20Proyecto.png)

5. En postman agregar la variable **accessToken**, **orderRefundId** e **idPayment**,  en Environment DESARROLLO. De esta manera, evitamos copiar a cada momento el token generado y los ids generados por el sistema.

   ![Postman Entorno](/Documentacion/Pantallas/Postman%20Variables%20Entorno.png)

6. En el IDE IntelliJ, ejecutar cada uno de los proyectos menos api-eureka-server-v1(se ejecuta en docker). Deben estar 8 proyectos levantados.

7. No se requiere ejecutar ningún script en base de datos, ya que se realizan los inserts al momento de ejecutar por primera vez (Solo en profile dev):
   - **api-accounting-v1**:  accountingdb(accounts) - se agregan 2 registros de cuentas (Cuentas contables).
   - **api-auth-service-v1**:  authdb(users, users_roles) - se agregan 5 usuarios con sus roles (supervisor, 3 empleados, 1 contador).
   - **api-talent-human-v1**: talenthumandb(employee) - se agregan 5 empleados que hacen referencia a los usuarios de authdb campo (user_id). 

## PROCESO (CREACIÓN-APROBACIÓN-PAGO) (Para ejecutar el flujo, desde postman, ejecutar en el siguiente orden).

1. /API-AUTH/Login Employee  - Logearse con usuario con rol EMPLEADO.
    ![Login Empleado](/Documentacion/Pantallas/01%20Login%20Empleado.png)

2. /API ORCHESTATOR/CREATE ORDER REFUND - Creación de Orden de Reembolso
    ![Creación Orden Reembolso](/Documentacion/Pantallas/02%20Create%20orden%20Refund.png)

3. Logs Creación de orden de reembolso.
    ![Logs Creación Orden de Reembolso](/Documentacion/Pantallas/03%20Logs%20Orcheatator%20Create%20Orden%20Refund.png)

4. /API-AUTH/Login Supervisor - Logearse con usuario con rol SUPERVISOR.
    ![Login Supervisor](/Documentacion/Pantallas/04%20Login%20Supervisor.png)

5. /API ORCHESTATOR/APPROVE ORDER REFUND - Aprobar orden de reembolso.
    ![Aprobar Orden de Reembolso](/Documentacion/Pantallas/05%20Approve%20Order%20Refund.png)
    
6. Logs Aprobación de orden de reembolso (SAGA - Logs de la ejecución )
    ![Logs Aprobar Orden de Reembolso](/Documentacion/Pantallas/06%20logs%20Approve%20Order%20Refund.png)

7. /API-AUTH/Login Contador  -  Logearse con usuario con rol CONTADOR.
    ![Login Empleado](/Documentacion/Pantallas/07%20Login%20User%20Contador.png)

8. /API ORCHESTATOR/PAYMENT ORDER REFUND 
    ![Pagar Orden de Pago de Reembolso](/Documentacion/Pantallas/08%20Payment%20Order%20Refund.png)

9. Logs procesar pago de orden de reembolso.
    ![Logs proceso de pago](/Documentacion/Pantallas/09%20Logs%20Payment%20Order%20Refund.png)

10. /API QUERY ORDER REFUND - Uso de api de consultas 
    ![Consulta Orden Pagada](/Documentacion/Pantallas/10%20API%20QUERY%20ORDER%20REFUND.png)


## PROCESO (CREACIÓN-RECHAZO) (Para ejecutar el flujo, desde postman, ejecutar en el siguiente orden).
    
1. /API-AUTH/Login Employee  - Logearse con usuario con rol EMPLEADO.
        ![Login Empleado](/Documentacion/Pantallas/01%20Login%20Empleado.png)

2. /API ORCHESTATOR/CREATE ORDER REFUND - Creación de Orden de Reembolso
   ![Creación Orden Reembolso](/Documentacion/Pantallas/11%20Create%20Order%20Reject.png)

3. /API-AUTH/Login Supervisor - Logearse con usuario con rol SUPERVISOR.
   ![Login Supervisor](/Documentacion/Pantallas/04%20Login%20Supervisor.png)

4. /API ORCHESTATOR/REJECT ORDER REFUND - Creación de Orden de Reembolso
   ![Rechazo orden de reembolso](/Documentacion/Pantallas/12%20Reject%20Order%20Refund.png)

5. Logs de rechazo de orden de reembolso.
   ![Logs Rechazo Orden Reembolso](/Documentacion/Pantallas/13%20Log%20%20Reject%20Order%20Refund.png)

## PROCESO (CREACION-APROBACION-COMPENSACION EN PAGO)
### Modificar property produce.mark-process-error a true en proyecto api-refund-v1 (archivo application-dev.yml)
```yml
produce:
    mark-process-error: true
```

1. /API-AUTH/Login Employee  - Logearse con usuario con rol EMPLEADO.
   ![Login Empleado](/Documentacion/Pantallas/01%20Login%20Empleado.png)

2. /API ORCHESTATOR/CREATE ORDER REFUND - Creación de Orden de Reembolso
   ![Login Empleado](/Documentacion/Pantallas/Saga%20Compensacion%20%20-%20Creacion%20Orden.png)

3. /API-AUTH/Login Supervisor - Logearse con usuario con rol SUPERVISOR.
   ![Login Supervisor](/Documentacion/Pantallas/04%20Login%20Supervisor.png)

5. /API ORCHESTATOR/APPROVE ORDER REFUND - Aprobar orden de reembolso.
   ![Compensacion Aprobacion](/Documentacion/Pantallas/Saga%20Compensacion%20%20-%20Aprobacion.png)

6. /API-AUTH/Login Contador - Logearse con usuario con rol CONTADOR.
   ![Login Empleado](/Documentacion/Pantallas/07%20Login%20User%20Contador.png)

7. /API ORCHESTATOR/PAYMENT ORDER REFUND  - Error producido, se genera Compensacion
   ![Compensacion Pago](/Documentacion/Pantallas/Saga%20Compensacion%20%20-%20Pago%20Error%20Compensacion.png)

8. Logs de error y compensacion.
   ![Compensacion Logs](/Documentacion/Pantallas/Saga%20Compensacion%20%20-%20Logs%20Compensacion%20en%20Payment.png)

   

