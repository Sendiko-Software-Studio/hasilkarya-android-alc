package com.system.alecsys.station.presentation

import com.system.alecsys.core.network.Status

sealed class StationQrScreenEvent {
    data class OnQrCodeScanned(val qrCode: String, val connectionStatus: Status) : StationQrScreenEvent()
}