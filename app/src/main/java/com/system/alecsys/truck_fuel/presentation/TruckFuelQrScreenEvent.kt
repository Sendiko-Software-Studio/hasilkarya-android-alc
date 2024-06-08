package com.system.alecsys.truck_fuel.presentation

import com.system.alecsys.core.network.Status
import com.system.alecsys.dashboard.presentation.component.ScanOptions

sealed class TruckFuelQrScreenEvent {

    data class OnTruckIdRegistered(val truckId: String, val connectionStatus: Status): TruckFuelQrScreenEvent()
    data class OnDriverIdRegistered(val driverId: String, val connectionStatus: Status): TruckFuelQrScreenEvent()
    data class OnStationIdRegistered(val stationId: String, val connectionStatus: Status): TruckFuelQrScreenEvent()
    data class OnVolumeRegistered(val volume: Double?): TruckFuelQrScreenEvent()
    data class OnNavigateForm(val scanOptions: ScanOptions): TruckFuelQrScreenEvent()
    data class OnOdometerChange(val odometer: String): TruckFuelQrScreenEvent()
    data object OnClearOdometer: TruckFuelQrScreenEvent()
    data class OnRemarksChange(val remarks: String): TruckFuelQrScreenEvent()
    data object OnClearRemarks: TruckFuelQrScreenEvent()
    data class SaveTruckFuelTransaction(val connectionStatus: Status): TruckFuelQrScreenEvent()
    data object NotificationClear: TruckFuelQrScreenEvent()
}