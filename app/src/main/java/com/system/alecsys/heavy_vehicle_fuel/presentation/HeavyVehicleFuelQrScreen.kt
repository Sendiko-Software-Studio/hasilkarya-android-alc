package com.system.alecsys.heavy_vehicle_fuel.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.system.alecsys.core.navigation.DashboardScreen
import com.system.alecsys.core.network.Status
import com.system.alecsys.core.ui.components.ContentBoxWithNotification
import com.system.alecsys.dashboard.presentation.component.ScanOptions
import com.system.alecsys.qr.presentation.QrScanComponent
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HeavyVehicleFuelQrScreen(
    state: HeavyVehicleFuelQrScreenState,
    onEvent: (HeavyVehicleFuelQrScreenEvent) -> Unit,
    onNavigateBack: (destination: Any) -> Unit,
) {
    LaunchedEffect(
        key1 = state.notificationMessage,
        key2 = state.isPostSuccessful,
        block = {
            if (state.notificationMessage.isNotBlank()) {
                delay(1000)
                onEvent(HeavyVehicleFuelQrScreenEvent.NotificationClear)
            }

            if (state.isPostSuccessful) {
                delay(1000)
                onNavigateBack(DashboardScreen)
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
                        visible = state.currentlyScanning == ScanOptions.HeavyVehicle,
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally()
                    ) {
                        QrScanComponent(
                            onResult = {
                                onEvent(
                                    HeavyVehicleFuelQrScreenEvent.OnHeavyVehicleIdRegistered(it)
                                )
                            },
                            navigateBack = { onNavigateBack(DashboardScreen) },
                            title = "Alat Berat",
                            isValid = state.heavyVehicleId.isNotBlank()
                        )
                    }
                    AnimatedVisibility(
                        visible = state.currentlyScanning == ScanOptions.Driver,
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally()
                    ) {
                        QrScanComponent(
                            onResult = {
                                onEvent(
                                    HeavyVehicleFuelQrScreenEvent.OnDriverIdRegistered(it)
                                )
                            },
                            navigateBack = {
                                onEvent(
                                    HeavyVehicleFuelQrScreenEvent.OnNavigateForm(
                                        ScanOptions.HeavyVehicle
                                    )
                                )
                            },
                            title = "Driver",
                            isValid = state.driverId.isNotBlank()
                        )
                    }
                    AnimatedVisibility(
                        visible = state.currentlyScanning == ScanOptions.Pos,
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally()
                    ) {
                        QrScanComponent(
                            onResult = {
                                onEvent(
                                    HeavyVehicleFuelQrScreenEvent.OnStationIdRegistered(it)
                                )
                            },
                            navigateBack = {
                                onEvent(
                                    HeavyVehicleFuelQrScreenEvent.OnNavigateForm(
                                        ScanOptions.Driver
                                    )
                                )
                            },
                            title = "Pos",
                            isValid = state.stationId.isNotBlank()
                        )
                    }
                    AnimatedVisibility(
                        visible = state.currentlyScanning == ScanOptions.Volume,
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally()
                    ) {
                        QrScanComponent(
                            onResult = {
                                onEvent(
                                    HeavyVehicleFuelQrScreenEvent.OnVolumeRegistered(it.toDoubleOrNull())
                                )
                            },
                            navigateBack = {
                                onEvent(
                                    HeavyVehicleFuelQrScreenEvent.OnNavigateForm(
                                        ScanOptions.Pos
                                    )
                                )
                            },
                            title = "Jumlah BBM",
                            isValid = state.volume != 0.0
                        )
                    }
                    AnimatedVisibility(
                        visible = state.currentlyScanning == ScanOptions.None,
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally()
                    ) {
                        HeavyVehicleFuelInputForm(
                            hourmeter = state.hourmeter,
                            hourmeterErrorState = state.hourmeterErrorState,
                            remarks = state.remarks,
                            onHourmeterChange = {
                                onEvent(
                                    HeavyVehicleFuelQrScreenEvent.OnHourmeterChange(
                                        it
                                    )
                                )
                            },
                            onHourmeterClear = { onEvent(HeavyVehicleFuelQrScreenEvent.OnClearHourmeter) },
                            onRemarksChange = {
                                onEvent(HeavyVehicleFuelQrScreenEvent.OnRemarksChange(it))
                            },
                            onRemarksClear = { onEvent(HeavyVehicleFuelQrScreenEvent.OnClearRemarks) },
                            onNavigateBack = {
                                onEvent(
                                    HeavyVehicleFuelQrScreenEvent.OnNavigateForm(
                                        it
                                    )
                                )
                            },
                            onSubmit = {
                                onEvent(
                                    HeavyVehicleFuelQrScreenEvent.SaveHeavyVehicleFuelTransaction
                                )
                            }
                        )
                    }
                }
            }
        }
    )
}