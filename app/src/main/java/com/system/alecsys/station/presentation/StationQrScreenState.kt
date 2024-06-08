package com.system.alecsys.station.presentation

import com.system.alecsys.core.network.Status
import com.system.alecsys.station.presentation.component.StationType

data class StationQrScreenState(
    val stationId: String = "",
    val isFlashOn: Boolean = false,
    val scanningFor: StationType = StationType.MINE,
    val isLoading: Boolean = false,
    val isRequestFailed: Boolean = false,
    val notificationMessage: String = "",
    val isRequestSuccess: Boolean = false,
    val connectionStatus: Status = Status.Available,
    val token: String = "",
)
