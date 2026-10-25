package cl.duoc.proyecto_pago_flex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cl.duoc.proyecto_pago_flex.ui.theme.Dimens
import cl.duoc.proyecto_pago_flex.ui.theme.Proyecto_Pago_FlexTheme

fun NuevoPagoScreen(
    onVolverClick: () -> Unit = {},
    onGuardarPago: (nombre: String, monto: String, fecha: String) -> Unit = { _,_, ->}
){
    var nombre by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Nuevo Pago") },
                navigationIcon = {
                    IconButton(onClick = onVolverClick){
                        Icon(
                            imageVector = Icons.AutoMirroged.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .padding(Dimens.paddingMedio),
            verticalArrangement = Arrangement.Top
        ){
            OutlinedTextField(
                value = nombre,
                onValueChange = {nombre = it},
                label = { Text("Nombre Del Destinatario")},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it},
                label = { Text("Fecha (ej: 18 Oct 2026")},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.weight(1f))

            BotonPrincipal(
                texto = "Guardar Pago",
                onClick = {
                    if( nombre.isNotBlank() && monto.isNotBlank()){
                        onGuardarPago(nombre, monto, fecha.isBlank{"Hoy"})
                        onVolverClick()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NuevoPagoScreenPreview(){
    Proyecto_Pago_FlexTheme{
        NuevoPagoScreen()
    }
}

