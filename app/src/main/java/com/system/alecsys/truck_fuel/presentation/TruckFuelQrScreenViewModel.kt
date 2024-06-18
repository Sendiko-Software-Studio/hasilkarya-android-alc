package com.system.alecsys.truck_fuel.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.alecsys.core.entities.FuelTruckEntity
import com.system.alecsys.core.network.NetworkConnectivityObserver
import com.system.alecsys.core.network.Status
import com.system.alecsys.core.repositories.fuel.truck.TruckFuelRepository
import com.system.alecsys.core.ui.utils.ErrorTextField
import com.system.alecsys.core.ui.utils.FailedRequest
import com.system.alecsys.core.utils.commaToPeriod
import com.system.alecsys.dashboard.data.CheckDriverIdResponse
import com.system.alecsys.dashboard.data.CheckStationIdResponse
import com.system.alecsys.dashboard.data.CheckTruckIdResponse
import com.system.alecsys.dashboard.presentation.component.ScanOptions
import com.system.alecsys.truck_fuel.data.TruckFuelRequest
import com.system.alecsys.truck_fuel.data.TruckFuelResponse
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
) : ViewModel() {

    private val _state = MutableStateFlow(TruckFuelQrScreenState())
    private val _userId = repository.getUserId()
    private val _token = repository.getToken()

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

    private fun postTruckFuel(fuelTruckEntity: FuelTruckEntity) {
        val token = "Bearer ${state.value.token}"
        val data = TruckFuelRequest(
            truckId = fuelTruckEntity.truckId,
            driverId = fuelTruckEntity.driverId,
            fuelStationId = fuelTruckEntity.stationId,
            volume = fuelTruckEntity.volume,
            odometer = fuelTruckEntity.odometer,
            fuelOperatorId = fuelTruckEntity.userId,
            remarks = fuelTruckEntity.remarks,
            date = fuelTruckEntity.date
        )
        val request = repository.postFuels(token, data)
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
    }

    // state related methods
    private fun onNavigateForm(scanOptions: ScanOptions) {
        _state.update {
            it.copy(currentlyScanning = scanOptions)
        }
    }

    private fun onTruckIdRegistered(truckId: String) {
        if (state.value.truckId.isEmpty()) {
            checkTruckId(truckId)
        }
    }

    private fun onDriverIdRegistered(driverId: String) {
        if (state.value.driverId.isEmpty()) {
            checkDriverId(driverId)
        }
    }

    private fun onStationIdRegistered(stationId: String) {
        if (state.value.stationId.isEmpty()) {
            checkStationId(stationId)
        }
    }

    private fun onVolumeRegistered(volume: Double?) {
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

    private fun saveTruckFuelTransaction() {
        if (state.value.odometer.isEmpty()) {
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
                date = LocalDateTime.now().toString(),
            )
            postTruckFuel(data)
        }
    }

    // event handler
    fun onEvent(event: TruckFuelQrScreenEvent) {
        when (event) {
            TruckFuelQrScreenEvent.NotificationClear -> onClearNotification()

            TruckFuelQrScreenEvent.OnClearRemarks -> onClearRemarks()

            is TruckFuelQrScreenEvent.OnNavigateForm -> onNavigateForm(event.scanOptions)

            is TruckFuelQrScreenEvent.OnTruckIdRegistered -> onTruckIdRegistered(
                event.truckId
            )

            is TruckFuelQrScreenEvent.OnDriverIdRegistered -> onDriverIdRegistered(
                event.driverId
            )

            is TruckFuelQrScreenEvent.OnStationIdRegistered -> onStationIdRegistered(
                event.stationId
            )

            is TruckFuelQrScreenEvent.OnVolumeRegistered -> onVolumeRegistered(event.volume)

            is TruckFuelQrScreenEvent.OnOdometerChange -> onOdometerChange(event.odometer)

            TruckFuelQrScreenEvent.OnClearOdometer -> onClearOdometer()

            is TruckFuelQrScreenEvent.OnRemarksChange -> onRemarksChange(event.remarks)

            is TruckFuelQrScreenEvent.SaveTruckFuelTransaction -> saveTruckFuelTransaction()

        }
    }
}