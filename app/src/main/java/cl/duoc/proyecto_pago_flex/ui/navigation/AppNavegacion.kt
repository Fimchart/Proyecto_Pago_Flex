package cl.duoc.proyecto_pago_flex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.proyecto_pago_flex.ui.screens.NuevoPagoScreen
import cl.duoc.proyecto_pago_flex.ui.screens.PagoFlexHomeScreen
import cl.duoc.proyecto_pago_flex.viewModel.PagoFlexViewModel

sealed class Pantalla(val ruta: String){
    object Home : Pantalla("home")
    object NuevoPago : Pantalla("nuevo_pago")
}

@Composable
fun AppNavegacion(
    pagoFlexViewModel: PagoFlexViewModel = viewModel()
){
    val navController = rememberNavController()
    val state by pagoFlexViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Pantalla.Home.ruta
    ){

        //Ruta pantalla principal
        composable(Pantalla.Home.ruta){
            PagoFlexHomeScreen(
                uiState = state,
                onMarcarPagado = { id -> pagoFlexViewModel.marcadoComoPagado(id) },
                onNavegarNuevoPago = { navController.navigate(Pantalla.NuevoPago.ruta) },
                onLimpiarMensaje = { pagoFlexViewModel.limpiarMensajeExito() }
            )
        }

        //Ruta pantalla nuevo pago
        composable(Pantalla.NuevoPago.ruta){
            NuevoPagoScreen(
                onRegistrarPago = { empresa, monto, fecha ->
                    pagoFlexViewModel.registrarCompromiso(empresa, monto, fecha)
                    navController.popBackStack() // Vuelve atrás al guardar
                },
                onVolver = { navController.popBackStack() } // Vuelve atrás al cancelar
            )
        }
    }
}