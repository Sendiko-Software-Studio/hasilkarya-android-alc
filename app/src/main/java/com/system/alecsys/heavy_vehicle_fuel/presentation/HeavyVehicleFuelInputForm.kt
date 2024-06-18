package com.system.alecsys.heavy_vehicle_fuel.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.system.alecsys.core.ui.components.NormalTextField
import com.system.alecsys.core.ui.theme.poppinsFont
import com.system.alecsys.core.ui.utils.ErrorTextField
import com.system.alecsys.dashboard.presentation.component.ScanOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeavyVehicleFuelInputForm(
    hourmeter: String,
    hourmeterErrorState: ErrorTextField,
    remarks: String,
    onHourmeterChange: (String) -> Unit,
    onHourmeterClear: () -> Unit,
    onRemarksChange: (String) -> Unit,
    onRemarksClear: () -> Unit,
    onNavigateBack: (ScanOptions) -> Unit,
    onSubmit: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Masukkan data", fontFamily = poppinsFont) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack(ScanOptions.Volume) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "kembali"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Isi jumlah hourmeter",
                    fontFamily = poppinsFont,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                NormalTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = hourmeter,
                    onNewValue = { onHourmeterChange(it) },
                    leadingIcon = Icons.Default.Speed,
                    onClearText = { onHourmeterClear() },
                    hint = "0.0",
                    keyboardType = KeyboardType.Decimal,
                    errorState = hourmeterErrorState
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Isi keterangan",
                    fontFamily = poppinsFont,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                NormalTextField(
                    modifier = Modifier.fillMaxWidth()
                        .height(128.dp),
                    value = remarks,
                    onNewValue = { onRemarksChange(it) },
                    leadingIcon = Icons.Default.Speed,
                    onClearText = { onRemarksClear() },
                    hint = "Keterangan",
                    shape = RoundedCornerShape(16.dp),
                    singleLine = false,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSubmit() },
                    content = {
                        Text(text = "Simpan data", fontFamily = poppinsFont)
                    },
                )
            }
        }
    }
}