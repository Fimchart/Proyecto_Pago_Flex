package cl.duoc.proyecto_pago_flex.viewModel

import androidx.lifecycle.ViewModel
import cl.duoc.proyecto_pago_flex.model.CompromisoPago
import cl.duoc.proyecto_pago_flex.model.EstadoPago
import cl.duoc.proyecto_pago_flex.model.PagoFlexUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PagoFlexViewModel: ViewModel() {

    // Estado reactivo mutable (privado)
    private val _uiState = MutableStateFlow(PagoFlexUiState())

    // Estado reactivo mutable (privado)
    val uiState: StateFlow<PagoFlexUiState> = _uiState.asStateFlow()


    fun registrarCompromiso(empresa: String, montoTexto: String, fecha: String) {
        val montoParsed = montoTexto.toDoubleOrNull()

        if (empresa.isBlank() || montoParsed == null || montoParsed <= 0) {
            return
        }

        val nuevoId = (_uiState.value.compromisos.maxOfOrNull { it.id } ?: 0) + 1

        val nuevoCompromiso = CompromisoPago(
            id = nuevoId,
            empresa = empresa,
            monto = montoParsed,
            fechaVencimiento = fecha.ifBlank { "Por definir" },
            estado = EstadoPago.PENDIENTE
        )

        _uiState.update { estadoActual ->
            estadoActual.copy(
                compromisos = listOf(nuevoCompromiso) + estadoActual.compromisos,
                mensajeExito = "Compromiso con $empresa registrado con exito"
            )
        }
    }

    // Regla de negocio: Cambiar el estado de un pago a PAGADO
    fun marcadoComoPagado(idCompromiso: Int){
        _uiState.update { estadoActual ->
            val listaActualizada = estadoActual.compromisos.map { compromiso ->
                if (compromiso.id == idCompromiso){
                    compromiso.copy(estado = EstadoPago.PAGADO)
                } else {
                    compromiso
                }
            }
            estadoActual.copy(
                compromisos = listaActualizada,
                mensajeExito = "Pago realizado con exito"
            )
        }
    }

    fun registrarRecomendacion(nombreOrganizacion: String) {
        _uiState.update { current ->
            current.copy(
                mensajeExito = "Recomendación de '$nombreOrganizacion' enviada con éxito (RF-12)"
            )
        }
    }

    // Limpiar mensaje emergente de confirmación
    fun limpiarMensajeExito(){
        _uiState.update { it.copy(mensajeExito = null) }
    }

}

