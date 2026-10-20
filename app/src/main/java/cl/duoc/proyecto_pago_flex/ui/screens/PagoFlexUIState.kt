package cl.duoc.proyecto_pago_flex.ui.screens

import cl.duoc.proyecto_pago_flex.R

data class Pago(
    val id: String,
    val nombreUsuario: String,
    val monto: String,
    val fecha: String,
    val avatarRes: Int = R.drawable.avatar_usuario
)
data class PagoFlexUIState(
    val listaPagos: List<Pago> = emptyList(),
    val isLoading: Boolean = false,
    val mensajeError: String? = null
)