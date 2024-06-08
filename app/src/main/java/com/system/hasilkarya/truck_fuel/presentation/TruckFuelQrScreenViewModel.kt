package com.system.hasilkarya.truck_fuel.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.hasilkarya.core.entities.FuelTruckEntity
import com.system.hasilkarya.core.network.NetworkConnectivityObserver
import com.system.hasilkarya.core.network.Status
import com.system.hasilkarya.core.repositories.fuel.truck.TruckFuelRepository
import com.system.hasilkarya.core.ui.utils.ErrorTextField
import com.system.hasilkarya.core.ui.utils.FailedRequest
import com.system.hasilkarya.core.utils.commaToPeriod
import com.system.hasilkarya.dashboard.data.CheckDriverIdResponse
import com.system.hasilkarya.dashboard.data.CheckStationIdResponse
import com.system.hasilkarya.dashboard.data.CheckTruckIdResponse
import com.system.hasilkarya.dashboard.presentation.component.ScanOptions
import com.system.hasilkarya.truck_fuel.data.TruckFuelRequest
import com.system.hasilkarya.truck_fuel.data.TruckFuelResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TruckFuelQrScreenViewModel @Inject constructor(
    private val repository: TruckFuelRepository,
    connectionObserver: NetworkConnectivityObserver
) : ViewModel() {

    private val _state = MutableStateFlow(TruckFuelQrScreenState())
    private val _userId = repository.getUserId()
    private val _token = repository.getToken()
    val _connectionStatus =
        combine(connectionObserver.observe(), _state) { connectionStatus, state ->
            state.copy(connectionStatus = connectionStatus)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TruckFuelQrScreenState())

    val state = combine(_userId, _token, _state) { userId, token, state ->
        state.copy(token = token, userId = userId)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TruckFuelQrScreenState())

    private fun checkTruckId(truckId: String) {
        _state.update { it.copy(isLoading = true) }
        val token = "Bearer ${state.value.token}"
        val request = repository.checkTruckId(token, truckId)
        request.enqueue(
            object : Callback<CheckTruckIdResponse> {
                override fun onResponse(
                    call: Call<CheckTruckIdResponse>,
                    response: Response<CheckTruckIdResponse>
                ) {
                    _state.update { it.copy(isLoading = false) }
                    when (response.code()) {
                        200 -> {
                            viewModelScope.launch {
                                _state.update {
                                    it.copy(truckId = truckId)
                                }
                                delay(1000)
                                _state.update {
                                    it.copy(currentlyScanning = ScanOptions.Driver)
                                }
                            }
                        }

                        else -> _state.update {
                            it.copy(
                                notificationMessage = "Qr invalid, mohon scan ulang",
                                isRequestFailed = FailedRequest(isFailed = true)
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<CheckTruckIdResponse>, t: Throwable) {
                    _state.update {
                        it.copy(
                            notificationMessage = "Qr invalid, mohon scan ulang",
                            isRequestFailed = FailedRequest(isFailed = true)
                        )
                    }
                }

            }
        )
    }

    private fun checkDriverId(driverId: String) {
        _state.update { it.copy(isLoading = true) }
        val token = "Bearer ${state.value.token}"
        val request = repository.checkDriverId(token, driverId)
        request.enqueue(
            object : Callback<CheckDriverIdResponse> {
                override fun onResponse(
                    call: Call<CheckDriverIdResponse>,
                    response: Response<CheckDriverIdResponse>
                ) {
                    _state.update { it.copy(isLoading = false) }
                    when (response.code()) {
                        200 -> {
                            viewModelScope.launch {
                                _state.update {
                                    it.copy(driverId = driverId)
                                }
                                delay(1000)
                                _state.update {
                                    it.copy(currentlyScanning = ScanOptions.Pos)
                                }
                            }
                        }

                        else -> _state.update {
                            it.copy(
                                notificationMessage = "Qr invalid, mohon scan ulang",
                                isRequestFailed = FailedRequest(isFailed = true)
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<CheckDriverIdResponse>, t: Throwable) {
                    _state.update {
                        it.copy(
                            notificationMessage = "Qr invalid, mohon scan ulang",
                            isRequestFailed = FailedRequest(isFailed = true)
                        )
                    }
                }

            }
        )
    }

    private fun checkStationId(stationId: String) {
        _state.update { it.copy(isLoading = true) }
        val token = "Bearer ${state.value.token}"
        val request = repository.checkStationId(token, stationId)
        request.enqueue(
            object : Callback<CheckStationIdResponse> {
                override fun onResponse(
                    call: Call<CheckStationIdResponse>,
                    response: Response<CheckStationIdResponse>
                ) {
                    _state.update { it.copy(isLoading = false) }
                    when (response.code()) {
                        200 -> {
                            viewModelScope.launch {
                                _state.update {
                                    it.copy(stationId = stationId)
                                }
                                delay(1000)
                                _state.update {
                                    it.copy(currentlyScanning = ScanOptions.Volume)
                                }
                            }
                        }

                        else -> _state.update {
                            it.copy(
                                notificationMessage = "Qr invalid, mohon scan ulang",
                                isRequestFailed = FailedRequest(isFailed = true)
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<CheckStationIdResponse>, t: Throwable) {
                    _state.update {
                        it.copy(
                            notificationMessage = "Qr invalid, mohon scan ulang",
                            isRequestFailed = FailedRequest(isFailed = true)
                        )
                    }
                }

            }
        )
    }

    private fun postTruckFuel(fuelTruckEntity: FuelTruckEntity, connectionStatus: Status) {
        val token = "Bearer ${state.value.token}"
        val data = TruckFuelRequest(
            truckId = fuelTruckEntity.truckId,
            driverId = fuelTruckEntity.driverId,
            stationId = fuelTruckEntity.stationId,
            volume = fuelTruckEntity.volume,
            odometer = fuelTruckEntity.odometer,
            fuelOperatorId = fuelTruckEntity.userId,
            remarks = fuelTruckEntity.remarks,
            date = fuelTruckEntity.date
        )
        val request = repository.postFuels(token, data)
        if (connectionStatus == Status.Available) {
            _state.update { it.copy(isLoading = true) }
            request.enqueue(
                object : Callback<TruckFuelResponse> {
                    override fun onResponse(
                        call: Call<TruckFuelResponse>,
                        response: Response<TruckFuelResponse>
                    ) {
                        _state.update { it.copy(isLoading = false) }
                        when (response.code()) {
                            201 -> _state.update {
                                it.copy(
                                    isPostSuccessful = true,
                                    notificationMessage = "Data tersimpan!"
                                )
                            }

                            else -> _state.update {
                                it.copy(
                                    isRequestFailed = FailedRequest(isFailed = true),
                                    notificationMessage = "Ups! Terjadi kesalahan."
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<TruckFuelResponse>, t: Throwable) {
                        viewModelScope.launch { repository.saveFuel(fuelTruckEntity) }
                        _state.update {
                            it.copy(
                                isPostSuccessful = true,
                                notificationMessage = "Data tersimpan!"
                            )
                        }
                    }

                }
            )
        } else {
            viewModelScope.launch { repository.saveFuel(fuelTruckEntity) }
            _state.update {
                it.copy(
                    isPostSuccessful = true,
                    notificationMessage = "Data tersimpan!"
                )
            }
        }
    }

    // state related methods
    private fun onNavigateForm(scanOptions: ScanOptions) {
        _state.update {
            it.copy(currentlyScanning = scanOptions)
        }
    }

    private fun onTruckIdRegistered(truckId: String, connectionStatus: Status) {
        if (connectionStatus == Status.Available) {
            checkTruckId(truckId)
        } else viewModelScope.launch {
            _state.update {
                it.copy(truckId = truckId)
            }
            delay(1000)
            _state.update {it.copy(currentlyScanning = ScanOptions.Driver) }
        }
    }

    private fun onDriverIdRegistered(driverId: String, connectionStatus: Status) {
        if (connectionStatus == Status.Available) {
            checkDriverId(driverId)
        } else viewModelScope.launch {
            _state.update { it.copy(driverId = driverId) }
            delay(1000)
            _state.update {it.copy(currentlyScanning = ScanOptions.Pos) }
        }
    }

    private fun onStationIdRegistered(stationId: String, connectionStatus: Status) {
        if (connectionStatus == Status.Available) {
            checkStationId(stationId)
        } else viewModelScope.launch {
            _state.update { it.copy(stationId = stationId) }
            delay(1000)
            _state.update {it.copy(currentlyScanning = ScanOptions.Volume) }
        }
    }

    private fun onVolumeRegistered(volume: Double?){
        if (volume == null) {
            _state.update { it.copy(notificationMessage = "Maaf, Qr invalid.") }
        } else {
            viewModelScope.launch {
                _state.update { it.copy(volume = volume) }
                delay(1000)
                _state.update { it.copy(currentlyScanning = ScanOptions.None) }
            }
        }
    }

    private fun onOdometerChange(odometer: String) = _state.update {
        it.copy(odometer = odometer)
    }

    private fun onClearOdometer() = _state.update {
        it.copy(odometer = "")
    }

    private fun onRemarksChange(remarks: String) = _state.update {
        it.copy(remarks = remarks)
    }

    private fun onClearRemarks() = _state.update {
        it.copy(remarks = "")
    }

    private fun onClearNotification() = _state.update {
        it.copy(notificationMessage = "")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveTruckFuelTransaction(connectionStatus: Status) {
        if (state.value.odometer.isEmpty()){
            _state.update {
                it.copy(
                    odometerErrorState = ErrorTextField(
                        isError = !it.odometerErrorState.isError,
                        errorMessage = "Odometer tidak boleh kosong."
                    )
                )
            }
        } else {
            val data = FuelTruckEntity(
                    truckId = state.value.truckId,
                    driverId = state.value.driverId,
                    stationId = state.value.stationId,
                    volume = state.value.volume,
                    userId = state.value.userId,
                    odometer = state.value.odometer.commaToPeriod().toDouble(),
                    remarks = state.value.remarks,
                    date = LocalDateTime.now().toString()
                )
            postTruckFuel(data, connectionStatus)
        }
    }

    // event handler
    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: TruckFuelQrScreenEvent) {
        when (event) {
            TruckFuelQrScreenEvent.NotificationClear -> onClearNotification()

            TruckFuelQrScreenEvent.OnClearRemarks -> onClearRemarks()

            is TruckFuelQrScreenEvent.OnNavigateForm -> onNavigateForm(event.scanOptions)

            is TruckFuelQrScreenEvent.OnTruckIdRegistered -> onTruckIdRegistered(event.truckId, event.connectionStatus)

            is TruckFuelQrScreenEvent.OnDriverIdRegistered -> onDriverIdRegistered(event.driverId, event.connectionStatus)

            is TruckFuelQrScreenEvent.OnStationIdRegistered -> onStationIdRegistered(event.stationId, event.connectionStatus)

            is TruckFuelQrScreenEvent.OnVolumeRegistered -> onVolumeRegistered(event.volume)

            is TruckFuelQrScreenEvent.OnOdometerChange -> onOdometerChange(event.odometer)

            TruckFuelQrScreenEvent.OnClearOdometer -> onClearOdometer()

            is TruckFuelQrScreenEvent.OnRemarksChange -> onRemarksChange(event.remarks)

            is TruckFuelQrScreenEvent.SaveTruckFuelTransaction -> saveTruckFuelTransaction(event.connectionStatus)

        }
    }
}