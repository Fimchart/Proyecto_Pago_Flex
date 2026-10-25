package cl.duoc.proyecto_pago_flex.model

// Modelo ti data para kadagiti comprobante a pendiante
data class ComprobantePendiente(
    val id: Int,
    val usuario: String,
    val monto: Double,
    val fecha: String
)

// Estado pantalla Principal
data class PagoFlexUiState(
    val usuarioNombre: String = "Camila Soto",
    val puntajePagador: Int = 850,
    val codigoReferido: String = "PAGOFLEX-CAMILA",
    val rolActivo: RolUsuario = RolUsuario.USUARIO,

    // Lista de compromisos financieros con ID numérico
    val compromisos: List<CompromisoPago> = listOf(
        CompromisoPago(1, "Inmobiliaria Norte", 250000.0, "15/10/2026", EstadoPago.PENDIENTE),
        CompromisoPago(2, "Instituto AIEP", 85000.0, "20/10/2026", EstadoPago.PENDIENTE),
        CompromisoPago(3, "Seguros Vida", 32000.0, "01/10/2026", EstadoPago.PAGADO)
    ),

    // Lista ti comprobante a pendientes (para ti rol ti Admin)
    val comprobantesPendientes: List<ComprobantePendiente> = listOf(
        ComprobantePendiente(101, "Juan Perez", 45000.0, "24/10/2026"),
        ComprobantePendiente(102, "Andrea Rojas", 120000.0, "24/10/2026"),
        ComprobantePendiente(103, "Gonzalo Soto", 30000.0, "25/10/2026")
    ),

    // Control ti modal/dialogo ti comprobantes
    val mostrarDialogoComprobantes: Boolean = false,

    // Feedback al usuario para manejar los mensajes emergentes de confirmación
    val mensajeExito: String? = null
)