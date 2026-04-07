# IMPLEMENTACIÓN DE MICROSERVICIOS EN UN SISTEMA DE LIQUIDACIÓN DE GASTOS PARA EMPLEADOS

Se presenta el trabajo final del Curso de   **Microservicios con Spring Cloud** . El proyecto se trata del flujo para la liquidación de gastos para empleados, implementando microservicios con Spring Cloud.

## Flujo del Proceso
1. **Registro de órden de reembolso.**
   1.1. Ingreso de la orden de reembolso y sus detalles.
   1.2. Consulta de datos de supervisor.
   1.2. Notificación a supervisor para la aprobación de la orden de reembolso.

2. **Aprobación de órden de reembolso (Implementación de SAGA).**
   2.2. Validación datos ingresados (Carga de datos: Empleado, Supervisor, Orden de Reembolso)
   2.3. Verificación de que la orden de reembolso debe ser aprobada por usuario logeado.
   2.4. Cambio de estado de la orden de reembolso (ORDERPAYGENERATED).
   2.5. Creación de registro de pago (CREATED).
   2.6. Notificación de orden de reembolso aprobada.

3. **Liquidación del valor de la órden de reembolso (Implementación de SAGA).**
   3.1.	Validación datos ingresados.
   3.2. Creación de asiento contable en base al pago de la orden de reembolso.
   3.3. Procesar pago (Cambio de estado a PROCESSED)
   3.4. Cambio de estado de la Orden de Pago a PAYED.
   3.5. Notificación de liquidación realizada.

4. **Rechazo de órden de reembolso.**
   4.1. Validación existencia de orden de reembolso y datos requeridos para el rechazo.
   4.2. Cambio de estado a REJECT.
   4.3. Notificación de orden de reembolso rechazada.

## Microservicios Implementados.

- **api-accounting-v1**: Api de contabilidad para registrar las transacciones o asientos contables que se generan al realizar un pago de unan orden de reembolso.
- **api-auth-service-v1**: Api para la autenticación de usuarios.
- **api-eureka-server-v1**: Servidor de descubrimiento de servicios.
- **api-notification-v1**: Api que emula el envio de notificaciones según el registro de orden de reembolso.
- **api-payment-v1**: Api que registra los pagos a procesar y los procesados en el flujo de liquidación de gastos.
- **api-refund-orchestator-v1**: Api orquestador de los 3 principales flujos (creación, aprobación, rechazo, liquidación).
- **api-refund-query-v1**: Api que implementa la lectura de ordenes de reembolso y los listeners para la actualización del estado de la liquidación.
- **api-refund-v1**: Api que permite el registro de las ordenes de reembolso, su cambio de estados.
- **api-talent-human-v1**: Api que permite ver la información de los empleados (emails, cuentas de ahorros, nombres, id de usuario).

## Instrucciones de Ejecución

1. Abrir proyecto en Intellij (Carpeta principal)
2. Importar archivo json de postman (./Documentacion/TRABAJO FINAL CURSO MICROSERVICIOS JAVIER PINTO.postman_collection.json)
3. Ejecutar comando, para crear red en docker .

```terminaloutput
docker network create ms-trabajo-final-jpinto
```
4. Ejecutar comando, para levantar ambiente
```terminaloutput
docker-compose --profile core up
```
5. En postman agregar la variable **accessToken** en Environment DESARROLLO. De esta manera, evitamos copiar a cada momento el token generado.

6. Ejecutar los scripts de base de datos:
    - Registro de usuarios
    - Registro de empleados
    - Registro de cuentas


7. Para ejecutar el flujo, desde postman, ejecutar en el siguiente orden.

    7.1. /API-AUTH/Login User  - Logearse con usuario con rol EMPLEADO.

    7.2. /API ORCHESTATOR/CREATE ORDER REFUND

    7.3. /API-AUTH/Login User  -  Logearse con usuario con rol SUPERVISOR.
    
    7.4. /API ORCHESTATOR/APPROVE ORDER REFUND

   7.3. /API-AUTH/Login User  -  Logearse con usuario con rol CONTADOR.

   7.4. /API ORCHESTATOR/PAYMENT ORDER REFUND

## microservicios-jpinto-sb-refund-order
Implementacion de Microservicios para realizar reembolsos a empleados por viaticos en empresa Danec
