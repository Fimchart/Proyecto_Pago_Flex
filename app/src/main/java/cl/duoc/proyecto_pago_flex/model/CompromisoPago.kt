package cl.duoc.proyecto_pago_flex.model


//Datos del negocio
enum class EstadoPago(val etiqueta: String) {
    PENDIENTE("Pendiente"),
    PAGADO("Pagado"),
    VENCIDO("Vencido")
}

data class CompromisoPago(
    val id: Int,
    val empresa: String,
    val monto: Double,
    val fechaVencimiento: String,
    val estado: EstadoPago
)