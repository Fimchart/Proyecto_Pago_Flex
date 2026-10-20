package cl.duoc.proyecto_pago_flex.ui

import androidx.compose.runtime.Composable
import cl.duoc.proyecto_pago_flex.ui.screens.viewmodel.NuevoPagoScreen
import cl.duoc.proyecto_pago_flex.ui.screens.viewmodel.PagoFlexHomeScreen
import cl.duoc.proyecto_pago_flex.ui.screens.viewmodel.PagoFlexViewModel

sealed class Destinos(val ruta: String){
    object Home: Destinos("Home")
    object NuevoPago: Destinos("nuevo_pago")
}

@Composable
fun AppNavegacion(
    viewModel: PagoFlexViewModel = viewModel()
){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinos.Home.ruta
    ){
        composable(Destinos.Home.ruta){
            PagoFlexHomeScreen(
                viewModel = viewModel,
                onNuevoPagoClick = {
                    navController.navigate(Destinos.NuevoPago.ruta)
                }
            )
        }
    }
    composable(Destinos.NuevoPago.ruta){
        NuevoPagoScreen(
            onVolverClick = {
                navController.popBackStack()
            },
            onGuardarPago = {nombre, monto, fecha -> viewModel.agregarPago(nombre, monto, fecha)
            }
        )
    }
}