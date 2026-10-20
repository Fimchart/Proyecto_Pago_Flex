package cl.duoc.proyecto_pago_flex.ui.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.proyecto_pago_flex.R
import cl.duoc.proyecto_pago_flex.ui.screens.Pago
import cl.duoc.proyecto_pago_flex.ui.screens.PagoFlexUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class PagoFlexViewModel: ViewModel(){

    private val _uiState = MutableStateFlow(PagoFlexUIState())
    val uiState: StateFlow<PagoFlexUIState> = _uiState.asStateFlow()

    init {
        cargarPagosIniciales()
    }

    private fun cargarPagosIniciales(){
        _uiState.update { it.copy(isLoading = true) }

        //simular la carga inicial de datos
        val pagosIniciales = listOf(
            Pago(UUID.randomUUID().toString(),"Juan Perez", "15.000", "17 Oct 2026", R.drawable.avatar_usuario),
            Pago(UUID.randomUUID().toString(), "Maria Gonzalez", "24.500","16 Oct 2026", R.drawable.avatar_usuario),
            Pago(UUID.randomUUID().toString(),"Carlos Silva", "8.000", "15 Oct 2026", R.drawable.avatar_usuario)
        )
        _uiState.update {
            it.copy(
                listaPagos = pagosIniciales,
                isLoading = false
            )
        }
    }
    //funcion de negrocio para agregar un nuevo pago

    fun agregarPago(nombre: String, monto: String, fecha: String){
        viewModelScope.launch {
            val nuevoPago = Pago(
                id = UUID.randomUUID().toString(),
                nombreUsuario = nombre,
                monto = monto,
                fecha = fecha,
                avatarRes = R.drawable.avatar_usuario
            )
            _uiState.update { estadoActual -> estadoActual.copy(
                listaPagos = listOf(nuevoPago) + estadoActual.listaPagos
            ) }
        }
    }
}