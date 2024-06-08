package com.system.alecsys.station.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.system.alecsys.core.ui.theme.poppinsFont

@Composable
fun StationLocation(
    modifier: Modifier = Modifier,
    stationName: String,
    onButtonClick: () -> Unit,
) {
    Column(modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Anda bertugas di: ", fontFamily = poppinsFont)
            TextButton(
                onClick = onButtonClick,
                content = {
                    Text(
                        text = if (stationName == "Tidak ada.") "Pilih pos" else "Ganti Pos",
                        fontFamily = poppinsFont
                    )
                }
            )
        }
        Text(
            text = stationName,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFont
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}