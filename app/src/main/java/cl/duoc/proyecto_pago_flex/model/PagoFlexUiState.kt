package cl.duoc.proyecto_pago_flex.model


//Estado pantalla Principal
data class PagoFlexUiState (
    val usuarioNombre: String = "Camila Soto",
    val puntajePagador: Int = 850,
    val codigoReferido: String = "PAGOFLEX-CAMILA",

    // Lista de compromisos financieros con ID numérico
    val compromisos: List<CompromisoPago> = listOf(
        CompromisoPago(1, "Inmobiliaria Norte", 250000.0, "15/10/2026", EstadoPago.PENDIENTE),
        CompromisoPago(2, "Instituto AIEP", 85000.0, "20/10/2026", EstadoPago.PENDIENTE),
        CompromisoPago(3, "Seguros Vida", 32000.0, "01/10/2026", EstadoPago.PAGADO)
    ),

// Feedback al usuario para manejar los mensajes emergentes de confirmación
val mensajeExito: String? = null
)