# IMPLEMENTACIÓN DE MICROSERVICIOS EN UN SISTEMA DE LIQUIDACIÓN DE GASTOS PARA EMPLEADOS

Se presenta el trabajo final del Curso de   **Microservicios con Spring Cloud**. El proyecto se trata del flujo para la liquidación de gastos para empleados, implementando microservicios con Spring Cloud.

## Flujo del Proceso
1. **Registro de órden de reembolso.**
   - Ingreso de la orden de reembolso y sus detalles.
   - Consulta de datos de supervisor.
   - Notificación a supervisor para la aprobación de la orden de reembolso.

2. **Aprobación de órden de reembolso (Implementación de SAGA).**
   - Validación datos ingresados (Carga de datos: Empleado, Supervisor, Orden de Reembolso)
   - Verificación de que la orden de reembolso debe ser aprobada por usuario logeado.
   - Cambio de estado de la orden de reembolso (ORDERPAYGENERATED).
   - Creación de registro de pago (CREATED).
   - Notificación de orden de reembolso aprobada.

3. **Liquidación(Pago) del valor de la órden de reembolso (Implementación de SAGA).**
   -	Validación datos ingresados.
   - Creación de asiento contable en base al pago de la orden de reembolso.
   - Procesar pago (Cambio de estado a PROCESSED)
   - Cambio de estado de la Orden de Pago a PAYED.
   - Notificación de liquidación realizada.

4. **Rechazo de órden de reembolso.**
   - Validación existencia de orden de reembolso y datos requeridos para el rechazo.
   - Cambio de estado a REJECT.
   - Notificación de orden de reembolso rechazada.

## Microservicios Implementados.

- **api-accounting-v1**: Api de contabilidad para registrar las transacciones o asientos contables que se generan al realizar un pago de unan orden de reembolso.
- **api-auth-service-v1**: Api para la autenticación de usuarios, creación de tokens, validación de token.
- **api-eureka-server-v1**: Servidor de descubrimiento de servicios.
- **api-notification-v1**: Api que emula el envio de notificaciones según el registro de orden de reembolso.
- **api-payment-v1**: Api que registra los pagos a procesar y los procesados en el flujo de liquidación de gastos.
- **api-refund-orchestator-v1**: Api orquestador de los 3 principales flujos (creación, aprobación, rechazo, liquidación).
- **api-refund-query-v1**: Api que implementa la lectura de ordenes de reembolso y los listeners para la actualización del estado de la liquidación.
- **api-refund-v1**: Api que permite el registro de las ordenes de reembolso, su cambio de estados.
- **api-talent-human-v1**: Api que permite ver la información de los empleados (emails, cuentas de ahorros, nombres, id de usuario).

## Instrucciones de Levantamiento de Infraestructura.

1. Descargar y abrir proyecto en Intellij (Carpeta principal). Cargar proyectos Maven. Ejecutar cada api desde IDE Intellij.
2. Importar archivo json de postman (./Documentacion/TRABAJO FINAL CURSO MICROSERVICIOS JAVIER PINTO.postman_collection.json)
3. Ejecutar comando, para crear red en docker .

```terminaloutput
docker network create ms-trabajo-final-jpinto
```
4. Ingresar a la carpeta /docker por la terminal y Ejecutar el siguiente comando, para levantar ambiente
```terminaloutput
docker-compose --profile core up
```
5. En postman agregar la variable **accessToken** en Environment DESARROLLO. De esta manera, evitamos copiar a cada momento el token generado.

6. Ejecutar los scripts de base de datos:
    - Registro de usuarios
    - Registro de empleados
    - Registro de cuentas


7. PROCESO (CREACIÓN-APROBACIÓN-PAGO) (Para ejecutar el flujo, desde postman, ejecutar en el siguiente orden).

    7.1. /API-AUTH/Login Employee  - Logearse con usuario con rol EMPLEADO.
    ![Login Empleado](/Documentacion/Pantallas/01%20Login%20Empleado.png)

    7.2. /API ORCHESTATOR/CREATE ORDER REFUND - Creación de Orden de Reembolso
    ![Creación Orden Reembolso](/Documentacion/Pantallas/02%20Create%20orden%20Refund.png)

    7.3. Logs Creación de orden de reembolso.
    ![Logs Creación Orden de Reembolso](/Documentacion/Pantallas/03%20Logs%20Orcheatator%20Create%20Orden%20Refund.png)

    7.4. /API-AUTH/Login Supervisor - Logearse con usuario con rol SUPERVISOR.
    ![Login Supervisor](/Documentacion/Pantallas/04%20Login%20Supervisor.png)

    7.5. /API ORCHESTATOR/APPROVE ORDER REFUND - Aprobar orden de reembolso (Copiar el código de orden de reembolso generado en la creación)
    ![Aprobar Orden de Reembolso](/Documentacion/Pantallas/05%20Approve%20Order%20Refund.png)
    
    7.6. Logs Aprobación de orden de reembolso (SAGA - Logs de la ejecución )
    ![Logs Aprobar Orden de Reembolso](/Documentacion/Pantallas/06%20logs%20Approve%20Order%20Refund.png)

    7.7. /API-AUTH/Login Contador  -  Logearse con usuario con rol CONTADOR.
    ![Login Empleado](/Documentacion/Pantallas/07%20Login%20User%20Contador.png)

    7.8. /API ORCHESTATOR/PAYMENT ORDER REFUND (Copiar id del pago registrado en el proceso de aprobación)
    ![Pagar Orden de Pago de Reembolso](/Documentacion/Pantallas/08%20Payment%20Order%20Refund.png)

    7.9. Logs procesar pago de orden de reembolso.
    ![Logs proceso de pago](/Documentacion/Pantallas/09%20Logs%20Payment%20Order%20Refund.png)

    7.10. /API QUERY ORDER REFUND - Uso de api de consultas 
    ![Consulta Orden Pagada](/Documentacion/Pantallas/10%20API%20QUERY%20ORDER%20REFUND.png)

8. PROCESO (CREACIÓN-RECHAZO) (Para ejecutar el flujo, desde postman, ejecutar en el siguiente orden).
    
    8.1. /API-AUTH/Login Employee  - Logearse con usuario con rol EMPLEADO.
        ![Login Empleado](/Documentacion/Pantallas/01%20Login%20Empleado.png)

    8.2. /API ORCHESTATOR/CREATE ORDER REFUND - Creación de Orden de Reembolso
   ![Creación Orden Reembolso](/Documentacion/Pantallas/11%20Create%20Order%20Reject.png)

   8.3. /API ORCHESTATOR/REJECT ORDER REFUND - Creación de Orden de Reembolso
   ![Rechazo orden de reembolso](/Documentacion/Pantallas/12%20Reject%20Order%20Refund.png)

    8.4. Logs de rechazo de orden de reembolso.
   ![Logs Rechazo Orden Reembolso](/Documentacion/Pantallas/13%20Log%20%20Reject%20Order%20Refund.png)
