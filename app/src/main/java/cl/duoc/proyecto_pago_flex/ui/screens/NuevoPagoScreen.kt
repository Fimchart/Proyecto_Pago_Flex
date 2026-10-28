package cl.duoc.proyecto_pago_flex.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import java.io.File

private val AzulPrimario = Color(0xFF1E3A8A)
private val AzulVibrante = Color(0xFF2563EB)
private val FondoSuave = Color(0xFFF1F5F9)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoPagoScreen(
    onRegistrarPago: (String, String, String) -> Unit,
    onVolver: () -> Unit
) {
    var empresa by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var errorMensaje by remember { mutableStateOf<String?>(null) }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imagenUri = uri
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempCameraUri != null) {
            imagenUri = tempCameraUri
        }
    }

    fun crearArchivoTemporalUri(): Uri {
        val file = File(context.getExternalFilesDir("Pictures"), "comprobante_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Compromiso", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    TextButton(onClick = onVolver) {
                        Text("← Volver", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AzulPrimario)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(FondoSuave)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Datos del Compromiso",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )

                    OutlinedTextField(
                        value = empresa,
                        onValueChange = { empresa = it; errorMensaje = null },
                        label = { Text("Empresa o Institución") },
                        placeholder = { Text("Ej: Inmobiliaria Norte") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = monto,
                        onValueChange = { monto = it; errorMensaje = null },
                        label = { Text("Monto ($)") },
                        placeholder = { Text("Ej: 150000") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = fecha,
                        onValueChange = { fecha = it; errorMensaje = null },
                        label = { Text("Fecha Vencimiento (DD/MM/AAAA)") },
                        placeholder = { Text("Ej: 15/11/2026") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Comprobante de respaldo (Opcional):",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF334155)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { galleryLauncher.launch("image/*") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Galería", fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = {
                                val uri = crearArchivoTemporalUri()
                                tempCameraUri = uri
                                cameraLauncher.launch(uri)
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Cámara", fontSize = 12.sp)
                        }
                    }

                    imagenUri?.let { uri ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFE2E8F0)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "Vista previa comprobante",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    errorMensaje?.let { err ->
                        Text(
                            text = err,
                            color = Color(0xFFDC2626),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onVolver,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Cancelar", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        if (empresa.isBlank() || monto.isBlank()) {
                            errorMensaje = "Por favor completa el nombre de la empresa y el monto."
                        } else if (monto.toDoubleOrNull() == null || monto.toDouble() <= 0) {
                            errorMensaje = "Ingresa un monto numérico válido mayor a 0."
                        } else {
                            onRegistrarPago(empresa, monto, fecha)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AzulVibrante),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Guardar", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}