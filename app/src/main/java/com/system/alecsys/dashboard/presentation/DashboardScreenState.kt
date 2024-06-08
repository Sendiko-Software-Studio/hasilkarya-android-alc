package com.system.alecsys.dashboard.presentation

import com.system.alecsys.core.entities.FuelHeavyVehicleEntity
import com.system.alecsys.core.entities.FuelTruckEntity
import com.system.alecsys.core.entities.MaterialEntity
import com.system.alecsys.core.entities.StationEntity
import com.system.alecsys.core.network.Status
import com.system.alecsys.core.ui.utils.FailedRequest

data class DashboardScreenState(
    val userId: String = "",
    val name: String = "",
    val token: String = "",
    val email: String = "",
    val password: String = "",
    val connectionStatus: Status = Status.UnAvailable,
    val isPostSuccessful: Boolean = false,
    val isRequestFailed: FailedRequest = FailedRequest(),
    val materials: List<MaterialEntity> = emptyList(),
    val role: String = "",
    val fuels: List<FuelTruckEntity> = emptyList(),
    val heavyFuels: List<FuelHeavyVehicleEntity> = emptyList(),
    val totalData: Int = 0,
    val stations: List<StationEntity> = emptyList(),
    val activeStation: StationEntity? = StationEntity(),
    val isUploading: Boolean = false,
    val isConnecting: Boolean = false,
    val notificationMessage: String = "",
    val isTokenExpired: Boolean = false,
)
