package cl.duoc.proyecto_pago_flex.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.proyecto_pago_flex.ui.theme.Proyecto_Pago_FlexTheme

private val AzulVibrante = Color(0xFF2563EB)
private val AzulOscuro = Color(0xFF1E3A8A)
private val FondoSuave = Color(0xFFF1F5F9)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoPagoScreen(
    onRegistrarPago: (empresa: String, monto: String, fecha: String) -> Unit,
    onVolver: () -> Unit
) {
    var empresa by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    // Estados de error para validaciones (IE 2.1.2)
    var empresaError by remember { mutableStateOf<String?>(null) }
    var montoError by remember { mutableStateOf<String?>(null) }
    var fechaError by remember { mutableStateOf<String?>(null) }

    fun validarFormulario(): Boolean {
        var esValido = true

        // Validar Empresa
        if (empresa.trim().isEmpty()) {
            empresaError = "El nombre de la empresa es obligatorio"
            esValido = false
        } else {
            empresaError = null
        }

        // Validar Monto (RN-17: Entero mayor a 0)
        val montoParsed = monto.toDoubleOrNull()
        if (montoParsed == null || montoParsed <= 0) {
            montoError = "Ingresa un monto válido mayor a $0"
            esValido = false
        } else {
            montoError = null
        }

        // Validar Fecha (DD/MM/AAAA)
        val regexFecha = Regex("""^\d{2}/\d{2}/\d{4}$""")
        if (!regexFecha.matches(fecha.trim())) {
            fechaError = "Formato de fecha inválido (Ej: 15/11/2026)"
            esValido = false
        } else {
            fechaError = null
        }

        return esValido
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Nuevo Compromiso",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Text("<", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AzulOscuro)
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(FondoSuave)
                .padding(16.dp)
        ) {
            Text(
                text = "Registrar nueva obligación",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
            Text(
                text = "Ingresa los datos del compromiso según las reglas del servicio.",
                fontSize = 13.sp,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Campo Empresa
            Column {
                OutlinedTextField(
                    value = empresa,
                    onValueChange = {
                        empresa = it
                        if (empresaError != null) empresaError = null
                    },
                    label = { Text("Empresa o servicio") },
                    placeholder = { Text("Ej: Enel, Colegio Bosque Nativo") },
                    isError = empresaError != null,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AzulVibrante,
                        unfocusedBorderColor = Color(0xFFCBD5E1)
                    )
                )
                AnimatedVisibility(
                    visible = empresaError != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    empresaError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                        )
                    }
                }
            }

            // Campo Monto
            Column {
                OutlinedTextField(
                    value = monto,
                    onValueChange = {
                        monto = it
                        if (montoError != null) montoError = null
                    },
                    label = { Text("Monto ($)") },
                    placeholder = { Text("Ej: 35000") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = montoError != null,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AzulVibrante,
                        unfocusedBorderColor = Color(0xFFCBD5E1)
                    )
                )
                AnimatedVisibility(
                    visible = montoError != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    montoError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                        )
                    }
                }
            }

            // Campo Fecha
            Column {
                OutlinedTextField(
                    value = fecha,
                    onValueChange = {
                        fecha = it
                        if (fechaError != null) fechaError = null
                    },
                    label = { Text("Fecha de vencimiento (ej: 30/10/2026)") },
                    placeholder = { Text("30/10/2026") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = fechaError != null,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AzulVibrante,
                        unfocusedBorderColor = Color(0xFFCBD5E1)
                    )
                )
                AnimatedVisibility(
                    visible = fechaError != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    fechaError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Guardar
            Button(
                onClick = {
                    if (validarFormulario()) {
                        onRegistrarPago(empresa.trim(), monto.trim(), fecha.trim())
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AzulVibrante)
            ) {
                Text(
                    "Guardar Compromiso",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Botón Cancelar
            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "Cancelar",
                    fontSize = 16.sp,
                    color = Color(0xFF64748B)
                )
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