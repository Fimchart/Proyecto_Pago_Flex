package cl.duoc.proyecto_pago_flex.ui.screens.viewmodel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.proyecto_pago_flex.ui.components.BotonPrincipal
import cl.duoc.proyecto_pago_flex.ui.components.TarjetaPago
import cl.duoc.proyecto_pago_flex.ui.theme.Dimens
import cl.duoc.proyecto_pago_flex.ui.theme.Proyecto_Pago_FlexTheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagoFlexHomeScreen(
    viewModel: PagoFlexViewModel = viewModel(),
    onNuevoPagoClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "PagoFlex",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Dimens.paddingMedio)
        ) {
            Text(
                text = "Historial de Pagos",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = Dimens.paddingMedio)
            )

            if (uiState.isLoading) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(uiState.listaPagos, key = { it.id }) { pago ->
                        TarjetaPago(
                            nombreUsuario = pago.nombreUsuario,
                            monto = pago.monto,
                            fecha = pago.fecha,
                            imagenAvatar = pago.avatarRes
                        )
                    }
                }
            }

            BotonPrincipal(
                texto = "Nuevo Pago",
                onClick = onNuevoPagoClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.paddingMedio)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PagoFlexHomeScreenPreview() {
    Proyecto_Pago_FlexTheme {
        PagoFlexHomeScreen()
    }
}