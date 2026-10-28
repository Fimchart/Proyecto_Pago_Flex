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

    // Registrar un nuevo compromiso financiero
    fun registrarCompromiso(empresa: String, montoTexto: String, fecha: String) {
        val montoParsed = montoTexto.toDoubleOrNull() ?: return
        if (empresa.isBlank() || montoParsed <= 0) return

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

    // NUEVA FUNCIÓN: Editar/Actualizar compromiso existente
    fun actualizarCompromiso(id: Int, nuevaEmpresa: String, nuevoMontoTexto: String, nuevaFecha: String) {
        val montoParsed = nuevoMontoTexto.toDoubleOrNull() ?: return
        if (nuevaEmpresa.isBlank() || montoParsed <= 0) return

        _uiState.update { estadoActual ->
            val listaActualizada = estadoActual.compromisos.map { item ->
                if (item.id == id) {
                    item.copy(
                        empresa = nuevaEmpresa,
                        monto = montoParsed,
                        fechaVencimiento = nuevaFecha.ifBlank { "Por definir" }
                    )
                } else {
                    item
                }
            }
            estadoActual.copy(
                compromisos = listaActualizada,
                mensajeExito = "Compromiso de $nuevaEmpresa actualizado con éxito"
            )
        }
    }

    // Marcar como pagado
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

    // Registrar recomendación de organización
    fun registrarRecomendacion(nombreOrganizacion: String) {
        _uiState.update { current ->
            current.copy(
                mensajeExito = "Recomendación de '$nombreOrganizacion' enviada con éxito (RF-12)"
            )
        }
    }

    // Cambiar de rol (Usuario, Admin, Ejecutivo)
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

    // Control de diálogo de comprobantes (Admin)
    fun abrirDialogoComprobantes() {
        _uiState.update { it.copy(mostrarDialogoComprobantes = true) }
    }

    fun cerrarDialogoComprobantes() {
        _uiState.update { it.copy(mostrarDialogoComprobantes = false) }
    }

    // Aprobar comprobante de transferencia
    fun aprobarComprobante(id: Int) {
        _uiState.update { current ->
            val listaFiltrada = current.comprobantesPendientes.filter { it.id != id }
            current.copy(
                comprobantesPendientes = listaFiltrada,
                mensajeExito = "Comprobante #$id aprobado correctamente"
            )
        }
    }

    // Rechazar comprobante de transferencia
    fun rechazarComprobante(id: Int) {
        _uiState.update { current ->
            val listaFiltrada = current.comprobantesPendientes.filter { it.id != id }
            current.copy(
                comprobantesPendientes = listaFiltrada,
                mensajeExito = "Comprobante #$id rechazado por inconsistencia"
            )
        }
    }

    // Enviar recordatorios de cobro (Ejecutivo)
    fun enviarRecordatoriosCobro() {
        _uiState.update { current ->
            current.copy(
                mensajeExito = "Recordatorios de cobro enviados a 12 clientes morosos"
            )
        }
    }

    // Exportar reporte de recaudación (Ejecutivo)
    fun exportarReporte() {
        _uiState.update { current ->
            current.copy(
                mensajeExito = "Reporte de recaudación generado y exportado en PDF"
            )
        }
    }

    // Limpiar alertas de éxito
    fun limpiarMensajeExito() {
        _uiState.update { it.copy(mensajeExito = null) }
    }
}