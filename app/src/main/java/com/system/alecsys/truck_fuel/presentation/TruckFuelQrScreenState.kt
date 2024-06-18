package com.system.alecsys.truck_fuel.presentation

import com.system.alecsys.core.entities.FuelTruckEntity
import com.system.alecsys.core.network.Status
import com.system.alecsys.core.ui.utils.ErrorTextField
import com.system.alecsys.core.ui.utils.FailedRequest
import com.system.alecsys.dashboard.presentation.component.ScanOptions

data class TruckFuelQrScreenState(
    val token: String = "",
    val notificationMessage: String = "",
    val isLoading: Boolean = false,
    val isPostSuccessful: Boolean = false,
    val isRequestFailed: FailedRequest = FailedRequest(),
    val currentlyScanning: ScanOptions = ScanOptions.Truck,
    val truckId: String = "",
    val driverId: String = "",
    val userId: String = "",
    val stationId: String = "",
    val volume: Double = 0.0,
    val odometer: String = "",
    val odometerErrorState: ErrorTextField = ErrorTextField(),
    val remarks: String = "",
    val gasList: List<FuelTruckEntity> = emptyList(),
)
