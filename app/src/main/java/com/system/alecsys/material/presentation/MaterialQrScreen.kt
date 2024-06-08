@file:OptIn(ExperimentalMaterial3Api::class)

package com.system.alecsys.material.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Outbound
import androidx.compose.material.icons.automirrored.filled.TextSnippet
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.system.alecsys.core.navigation.DashboardScreen
import com.system.alecsys.core.navigation.StationScreen
import com.system.alecsys.core.ui.components.ContentBoxWithNotification
import com.system.alecsys.core.ui.components.NormalTextField
import com.system.alecsys.core.ui.theme.poppinsFont
import com.system.alecsys.dashboard.presentation.component.ScanOptions.None
import com.system.alecsys.dashboard.presentation.component.ScanOptions.Pos
import com.system.alecsys.dashboard.presentation.component.ScanOptions.Truck
import com.system.alecsys.qr.presentation.QrScanComponent
import com.system.alecsys.station.presentation.component.StationLocation
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialQrScreen(
    state: MaterialQrScreenState,
    onEvent: (MaterialQrScreenEvent) -> Unit,
    onNavigateBack: (destination: Any) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(
        key1 = state,
        block = {
            if (state.isPostSuccessful) {
                delay(1000)
                onNavigateBack(DashboardScreen)
            }

            if (state.notificationMessage.isNotBlank()) {
                delay(2000)
                onEvent(MaterialQrScreenEvent.OnClearNotification)
            }
        }
    )
    ContentBoxWithNotification(
        message = state.notificationMessage,
        isLoading = state.isLoading,
        isErrorNotification = state.isRequestFailed.isFailed,
        content = {
            Scaffold { _ ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    AnimatedVisibility(
                        visible = state.currentlyScanning == Truck,
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally(),
                        content = {
                            QrScanComponent(
                                onResult = {
                                    onEvent(MaterialQrScreenEvent.OnTruckIdRegistered(it))
                                },
                                navigateBack = {
                                    onNavigateBack(DashboardScreen)
                                },
                                title = "Truck",
                                isValid = state.truckId.isNotBlank()
                            )
                        }
                    )
                    AnimatedVisibility(visible = state.currentlyScanning == None) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = { Text(text = "Masukkan data", fontFamily = poppinsFont) },
                                    navigationIcon = {
                                        IconButton(onClick = { onEvent(MaterialQrScreenEvent.OnNavigateForm(Pos)) }) {
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
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    StationLocation(
                                        modifier = Modifier.padding(horizontal = 8.dp),
                                        stationName = if (state.stationName == "Station berhasil disimpan.") {
                                            "Pos baru disimpan."
                                        } else {
                                            "${state.stationName}."
                                        },
                                        onButtonClick = {
                                            onNavigateBack(StationScreen)
                                        }
                                    )
                                    Text(
                                        text = "Masukkan volume material",
                                        fontFamily = poppinsFont,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    NormalTextField(
                                        modifier = Modifier.fillMaxWidth(),
                                        value = state.materialVolume,
                                        onNewValue = { onEvent(MaterialQrScreenEvent.OnVolumeChange(it)) },
                                        leadingIcon = Icons.AutoMirrored.Default.Outbound,
                                        onClearText = {  },
                                        keyboardType = KeyboardType.Decimal,
                                        hint = "Dalam meter kubik (m³)",
                                        errorState = state.materialVolumeErrorState
                                    )
                                    Text(
                                        text = "Tambah keterangan",
                                        fontFamily = poppinsFont,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    NormalTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(128.dp),
                                        value = state.remarks,
                                        onNewValue = { onEvent(MaterialQrScreenEvent.OnNewRemarks(it)) },
                                        leadingIcon = Icons.AutoMirrored.Filled.TextSnippet,
                                        onClearText = {  },
                                        hint = "Keterangan",
                                        shape = RoundedCornerShape(16.dp),
                                        singleLine = false,
                                    )
                                    Button(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            onEvent(MaterialQrScreenEvent.SaveMaterial)
                                        }
                                    ) {
                                        Text(text = "Simpan data", fontFamily = poppinsFont)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}