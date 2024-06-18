package com.system.alecsys.truck_fuel.presentation

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
import com.system.alecsys.heavy_vehicle_fuel.presentation.HeavyVehicleFuelQrScreenEvent
import com.system.alecsys.qr.presentation.QrScanComponent
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TruckFuelQrScreen(
    state: TruckFuelQrScreenState,
    onEvent: (TruckFuelQrScreenEvent) -> Unit,
    onNavigateBack: (destination: Any) -> Unit,
) {
    LaunchedEffect(
        key1 = state.notificationMessage,
        key2 = state.isPostSuccessful,
        block = {
            if (state.notificationMessage.isNotBlank()) {
                delay(1000)
                onEvent(TruckFuelQrScreenEvent.NotificationClear)
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
                        visible = state.currentlyScanning == ScanOptions.Truck,
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally()
                    ) {
                        QrScanComponent(
                            onResult = {
                                onEvent(TruckFuelQrScreenEvent.OnTruckIdRegistered(it))
                            },
                            navigateBack = { onNavigateBack(DashboardScreen) },
                            title = "Truck",
                            isValid = state.truckId.isNotBlank()
                        )
                    }
                    AnimatedVisibility(
                        visible = state.currentlyScanning == ScanOptions.Driver,
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally()
                    ) {
                        QrScanComponent(
                            onResult = {
                                onEvent(TruckFuelQrScreenEvent.OnDriverIdRegistered(it))
                            },
                            navigateBack = {
                                onEvent(
                                    TruckFuelQrScreenEvent.OnNavigateForm(
                                        ScanOptions.Truck
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
                                onEvent(TruckFuelQrScreenEvent.OnStationIdRegistered(it))
                            },
                            navigateBack = {
                                onEvent(
                                    TruckFuelQrScreenEvent.OnNavigateForm(
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
                            onResult = { onEvent(TruckFuelQrScreenEvent.OnVolumeRegistered(it.toDoubleOrNull())) },
                            navigateBack = {
                                onEvent(
                                    TruckFuelQrScreenEvent.OnNavigateForm(
                                        ScanOptions.Pos
                                    )
                                )
                            },
                            title = "Jumlah BBM",
                            isValid = state.volume != 0.0
                        )
                    }
                    AnimatedVisibility(visible = state.currentlyScanning == ScanOptions.None) {
                        FuelInputForm(
                            odometer = state.odometer,
                            odometerErrorState = state.odometerErrorState,
                            remarks = state.remarks,
                            onOdometerChange = { onEvent(TruckFuelQrScreenEvent.OnOdometerChange(it)) },
                            onOdometerClear = { onEvent(TruckFuelQrScreenEvent.OnClearOdometer) },
                            onRemarksChange = { onEvent(TruckFuelQrScreenEvent.OnRemarksChange(it)) },
                            onRemarksClear = { onEvent(TruckFuelQrScreenEvent.OnClearRemarks) },
                            onNavigateBack = { onEvent(TruckFuelQrScreenEvent.OnNavigateForm(it)) },
                            onSubmit = {
                                onEvent(
                                    TruckFuelQrScreenEvent.SaveTruckFuelTransaction
                                )
                            }
                        )
                    }
                }
            }
        }
    )
}