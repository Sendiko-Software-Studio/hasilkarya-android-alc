package com.system.alecsys.material.presentation

import com.system.alecsys.core.network.Status
import com.system.alecsys.core.ui.utils.ErrorTextField
import com.system.alecsys.core.ui.utils.FailedRequest
import com.system.alecsys.dashboard.presentation.component.ScanOptions

data class MaterialQrScreenState(
    val currentlyScanning: ScanOptions = ScanOptions.Truck,
    val driverId: String = "",
    val truckId: String = "",
    val stationId: String = "",
    val materialVolume: String = "",
    val materialVolumeErrorState: ErrorTextField = ErrorTextField(),
    val remarks: String = "",
    val token: String = "",
    val connectionStatus: Status = Status.UnAvailable,
    val isPostSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val notificationMessage: String = "",
    val isRequestFailed: FailedRequest = FailedRequest(),
    val userId: String = "",
    val stationName: String = ""
)