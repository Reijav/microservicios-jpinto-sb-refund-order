**Sistema de Reembolsos**



* **Creacion de Orden de Reembolso (Empleado)**
	Validacion datos orden de reembolso ( validar  facturas , notas de venta)
* &nbsp;	Validacion catalogos(Tipo Reembolso,Motivo Reembolso)
* &nbsp;	Registro de orden                                    ->       Creacion Orden de reembolso (nro\_orden, id\_empleado)
* &nbsp;	Notificacion de reembolso por aprobar a Jefe inmediato.
* **Aprobación Orden de Reembolso (Jefe Inmediato)**
* &nbsp;	Aprobar
* &nbsp;		Actualizar Orden
* &nbsp;		Envio orden reembolso a sistema contable (Generacion asiento contable). Obtención asiento contable
* &nbsp;		Generacion de Pago ().
* &nbsp;		Notificacion de orden de reembolso aprobada a empleado.



*  	Rechazar

 		Registro Observaciones

 		Notificacion de orden de reembolso rechazada.





* **Liquidación reembolso**
	Consulta liquidaciones por ejecutar
* &nbsp;	Enviar orden de liquidacion por medio de transaccion bancaria. 
* &nbsp;	Notificacion de liquidacion por reembolso realizada.









Employee

* id
* userId
* fullName
* immediateSupervisorId
* position





RefundOrder

* id (UUID)
* employeeId (Long)
* dateOrder (Date)
* motiveId (Long)
* totalValue (BigDecimal)
* approverId (Long)
* state (CREATED, APROVED, REJECTED, ORDERPAYGENERATED,PAYED)

* paymentId







RefundBills

* refundId(UUID)
* providerRuc(String)
* providerName(tring)
* billNumber(String)
* detail
* value
* billFile(String)





Payments

* id
* payee\_type
* amount
* payment\_method
* payment\_date
* transaction\_id



Transaction

* id
* description
* transactionDate
* createdBy
* status
* createdAt





Transactionlines

* id
* TransactionId
* accountId
* debit
* credit
* description







Account

* id
* Code
* name
* type











