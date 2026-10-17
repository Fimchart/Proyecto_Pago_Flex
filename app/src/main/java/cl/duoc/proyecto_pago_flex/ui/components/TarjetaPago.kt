package cl.duoc.proyecto_pago_flex.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cl.duoc.proyecto_pago_flex.R
import cl.duoc.proyecto_pago_flex.ui.theme.Dimens
import cl.duoc.proyecto_pago_flex.ui.theme.Proyecto_Pago_FlexTheme

@Composable
fun TarjetaPago(
    nombreUsuario: String,
    monto: String,
    fecha: String,
    @DrawableRes imagenAvatar: Int,
    modifier: Modifier = Modifier
){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.espacioMedio, vertical = Dimens.espacioChico),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.espacioMedio),
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.espacioMedio)
        ) {
            AvatarCircular(
                imagen = imagenAvatar,
                descripcion = "Foto de $nombreUsuario"
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = nombreUsuario,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = fecha,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = monto,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TarjetaPagoPreview(){
    Proyecto_Pago_FlexTheme{
        TarjetaPago(
            nombreUsuario = "Juan Pérez",
            monto = "$15.000",
            fecha = "17 Oct 2026",
            imagenAvatar = R.drawable.avatar_usuario
        )
    }
}