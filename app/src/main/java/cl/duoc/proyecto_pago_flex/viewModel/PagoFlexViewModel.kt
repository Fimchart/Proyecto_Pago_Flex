package cl.duoc.proyecto_pago_flex.viewModel

import androidx.lifecycle.ViewModel
import cl.duoc.proyecto_pago_flex.model.CompromisoPago
import cl.duoc.proyecto_pago_flex.model.EstadoPago
import cl.duoc.proyecto_pago_flex.model.PagoFlexUiState
import cl.duoc.proyecto_pago_flex.model.RolUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PagoFlexViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PagoFlexUiState())
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
                mensajeExito = "Compromiso con $empresa registrado con éxito"
            )
        }
    }

    fun marcadoComoPagado(idCompromiso: Int) {
        _uiState.update { estadoActual ->
            val listaActualizada = estadoActual.compromisos.map { compromiso ->
                if (compromiso.id == idCompromiso) {
                    compromiso.copy(estado = EstadoPago.PAGADO)
                } else {
                    compromiso
                }
            }
            estadoActual.copy(
                compromisos = listaActualizada,
                mensajeExito = "Pago realizado con éxito"
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

    // Actualización para simular dinámicamente el nombre según el rol
    fun cambiarRol(nuevoRol: RolUsuario) {
        val nombreSimulado = when (nuevoRol) {
            RolUsuario.USUARIO -> "Camila Soto"
            RolUsuario.ADMIN -> "Carlos (Agente)"
            RolUsuario.EJECUTIVO -> "María (Ejecutiva)"
        }

        _uiState.update { current ->
            current.copy(
                rolActivo = nuevoRol,
                usuarioNombre = nombreSimulado,
                mensajeExito = "Sesión simulada: ${nuevoRol.titulo}"
            )
        }
    }

    // Acciones para modal de comprobantes Admin
    fun abrirDialogoComprobantes() {
        _uiState.update { it.copy(mostrarDialogoComprobantes = true) }
    }

    fun cerrarDialogoComprobantes() {
        _uiState.update { it.copy(mostrarDialogoComprobantes = false) }
    }

    fun aprobarComprobante(id: Int) {
        _uiState.update { current ->
            val listaFiltrada = current.comprobantesPendientes.filter { it.id != id }
            current.copy(
                comprobantesPendientes = listaFiltrada,
                mensajeExito = "Comprobante #$id aprobado correctamente"
            )
        }
    }

    fun rechazarComprobante(id: Int) {
        _uiState.update { current ->
            val listaFiltrada = current.comprobantesPendientes.filter { it.id != id }
            current.copy(
                comprobantesPendientes = listaFiltrada,
                mensajeExito = "Comprobante #$id rechazado por inconsistencia"
            )
        }
    }

    // Acciones para panel Ejecutivo
    fun enviarRecordatoriosCobro() {
        _uiState.update { current ->
            current.copy(
                mensajeExito = "Recordatorios de cobro enviados a 12 clientes morosos"
            )
        }
    }

    fun exportarReporte() {
        _uiState.update { current ->
            current.copy(
                mensajeExito = "Reporte de recaudación generado y exportado en PDF"
            )
        }
    }

    fun limpiarMensajeExito() {
        _uiState.update { it.copy(mensajeExito = null) }
    }
}