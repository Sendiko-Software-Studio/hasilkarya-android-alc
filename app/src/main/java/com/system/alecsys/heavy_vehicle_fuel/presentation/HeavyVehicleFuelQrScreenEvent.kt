package com.system.alecsys.heavy_vehicle_fuel.presentation

import com.system.alecsys.core.network.Status
import com.system.alecsys.dashboard.presentation.component.ScanOptions

sealed class HeavyVehicleFuelQrScreenEvent {
    data class OnHeavyVehicleIdRegistered(val vHId: String, val connectionStatus: Status): HeavyVehicleFuelQrScreenEvent()
    data class OnDriverIdRegistered(val driverId: String, val connectionStatus: Status): HeavyVehicleFuelQrScreenEvent()
    data class OnStationIdRegistered(val stationId: String, val connectionStatus: Status): HeavyVehicleFuelQrScreenEvent()
    data class OnVolumeRegistered(val volume: Double?): HeavyVehicleFuelQrScreenEvent()
    data class OnNavigateForm(val scanOptions: ScanOptions): HeavyVehicleFuelQrScreenEvent()
    data class OnHourmeterChange(val odometer: String): HeavyVehicleFuelQrScreenEvent()
    data object OnClearHourmeter: HeavyVehicleFuelQrScreenEvent()
    data class OnRemarksChange(val remarks: String): HeavyVehicleFuelQrScreenEvent()
    data object OnClearRemarks: HeavyVehicleFuelQrScreenEvent()
    data class SaveHeavyVehicleFuelTransaction(val connectionStatus: Status): HeavyVehicleFuelQrScreenEvent()
    data object NotificationClear: HeavyVehicleFuelQrScreenEvent()

}
