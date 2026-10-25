package cl.duoc.proyecto_pago_flex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cl.duoc.proyecto_pago_flex.model.EstadoPago
import cl.duoc.proyecto_pago_flex.model.PagoFlexUiState
import cl.duoc.proyecto_pago_flex.ui.theme.Proyecto_Pago_FlexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagoFlexHomeScreen(
    uiState: PagoFlexUiState,
    onMarcarPagado: (Int) -> Unit,
    onNavegarNuevoPago: () -> Unit,
    onLimpiarMensaje: () -> Unit
) {
    val snackbarHostState = SnackbarHostState()

    //Feedback al usuario cuando se registra o paga un compromiso
    LaunchedEffect(uiState.mensajeExito) {
        uiState.mensajeExito?.let { mensaje ->
            snackbarHostState.showSnackbar(mensaje)
            onLimpiarMensaje()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavegarNuevoPago) {
                Text("+", style = MaterialTheme.typography.titleLarge)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            //Header con datos del perfil
            Text(
                text = "Hola, ${uiState.usuarioNombre}",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Puntaje Pagador: ${uiState.puntajePagador} pts",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tus compromisos financieros",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            //Lista dinamica de compromisos
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.compromisos, key = {it.id}) { compromiso ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = compromiso.empresa,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(text = "Monto: $${compromiso.monto}")
                                Text(text = "Vence: ${compromiso.fechaVencimiento}")
                                Text(text = "Estado: ${compromiso.estado.etiqueta}")
                            }

                            if (compromiso.estado != EstadoPago.PAGADO){
                                Button(onClick = { onMarcarPagado(compromiso.id) }) {
                                    Text("Pagar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PagoFlexHomeScreenPreview() {
    Proyecto_Pago_FlexTheme {
        PagoFlexHomeScreen(
            uiState = PagoFlexUiState(
                usuarioNombre = "Carlos",
                puntajePagador = 850,
                compromisos = listOf(
                    cl.duoc.proyecto_pago_flex.model.CompromisoPago(
                        id = 1,
                        empresa = "Enel",
                        monto = 35000.0,
                        fechaVencimiento = "15/11/2026",
                        estado = EstadoPago.PENDIENTE
                    ),
                    cl.duoc.proyecto_pago_flex.model.CompromisoPago(
                        id = 2,
                        empresa = "Movistar",
                        monto = 19990.0,
                        fechaVencimiento = "20/11/2026",
                        estado = EstadoPago.PAGADO
                    )
                )
            ),
            onMarcarPagado = {},
            onNavegarNuevoPago = {},
            onLimpiarMensaje = {}
        )
    }
}