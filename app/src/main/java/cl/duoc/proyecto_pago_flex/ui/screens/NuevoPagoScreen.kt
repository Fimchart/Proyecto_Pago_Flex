package cl.duoc.proyecto_pago_flex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cl.duoc.proyecto_pago_flex.ui.theme.Proyecto_Pago_FlexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoPagoScreen(
    onRegistrarPago: (String, String, String) -> Unit,
    onVolver: () -> Unit
) {
    var empresa by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Compromiso") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Text("<")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = empresa,
                onValueChange = { empresa = it },
                label = { Text("Empresa o servicio") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = monto,
                onValueChange = { monto = it },
                label = { Text("Monto ($)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha de vencimiento (ej: 30/10/2026)") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { onRegistrarPago(empresa, monto, fecha) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Compromiso")
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NuevoPagoScreenPreview() {
    Proyecto_Pago_FlexTheme {
        NuevoPagoScreen(
            onRegistrarPago = { _, _, _ -> },
            onVolver = {}
        )
    }
}