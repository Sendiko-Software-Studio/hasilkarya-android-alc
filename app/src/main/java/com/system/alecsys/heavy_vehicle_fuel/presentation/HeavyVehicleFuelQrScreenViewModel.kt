package com.system.alecsys.heavy_vehicle_fuel.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.alecsys.core.entities.FuelHeavyVehicleEntity
import com.system.alecsys.core.network.NetworkConnectivityObserver
import com.system.alecsys.core.network.Status
import com.system.alecsys.core.repositories.fuel.heavy_vehicle.HeavyVehicleFuelRepository
import com.system.alecsys.core.ui.utils.ErrorTextField
import com.system.alecsys.core.ui.utils.FailedRequest
import com.system.alecsys.core.utils.commaToPeriod
import com.system.alecsys.dashboard.data.CheckDriverIdResponse
import com.system.alecsys.dashboard.data.CheckStationIdResponse
import com.system.alecsys.dashboard.presentation.component.ScanOptions
import com.system.alecsys.heavy_vehicle_fuel.data.CheckHeavyVehicleIdResponse
import com.system.alecsys.heavy_vehicle_fuel.data.HeavyVehicleFuelRequest
import com.system.alecsys.heavy_vehicle_fuel.data.HeavyVehicleFuelResponse
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
class HeavyVehicleFuelQrScreenViewModel @Inject constructor(
    private val repository: HeavyVehicleFuelRepository,
    connectionObserver: NetworkConnectivityObserver
) : ViewModel() {

    private val _state = MutableStateFlow(HeavyVehicleFuelQrScreenState())
    private val _userId = repository.getUserId()
    private val _token = repository.getToken()
    val connectionStatus =
        combine(connectionObserver.observe(), _state) { connectionStatus, state ->
            state.copy(connectionStatus = connectionStatus)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HeavyVehicleFuelQrScreenState()
        )
    val state = combine(_userId, _token, _state) { userId, token, state ->
        state.copy(
            userId = userId,
            token = token
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HeavyVehicleFuelQrScreenState())

    // checking ids
    private fun checkHeavyVehicleId(heavyVehicleId: String) {
        _state.update { it.copy(isLoading = true) }
        val token = "Bearer ${state.value.token}"
        val request = repository.checkHeavyVehicleId(token, heavyVehicleId)
        request.enqueue(
            object : Callback<CheckHeavyVehicleIdResponse> {
                override fun onResponse(
                    call: Call<CheckHeavyVehicleIdResponse>,
                    response: Response<CheckHeavyVehicleIdResponse>
                ) {
                    Log.i("DEBUG", "onResponse: active")
                    _state.update { it.copy(isLoading = false) }
                    when (response.code()) {
                        200 -> {
                            viewModelScope.launch {
                                _state.update {
                                    it.copy(
                                        heavyVehicleId = heavyVehicleId,
                                    )
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

                override fun onFailure(call: Call<CheckHeavyVehicleIdResponse>, t: Throwable) {
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

    // post data
    private fun postHeavyVehicleFuel(
        heavyVehicleEntity: FuelHeavyVehicleEntity,
        connectionStatus: Status
    ) {
        val token = "Bearer ${state.value.token}"
        val data = HeavyVehicleFuelRequest(
            heavyVehicleId = heavyVehicleEntity.heavyVehicleId,
            driverId = heavyVehicleEntity.driverId,
            stationId = heavyVehicleEntity.stationId,
            fuelOperatorId = heavyVehicleEntity.gasOperatorId,
            volume = heavyVehicleEntity.volume,
            hourmeter = heavyVehicleEntity.hourmeter,
            remarks = heavyVehicleEntity.remarks,
            date = heavyVehicleEntity.date
        )
        if (connectionStatus == Status.Available) {
            _state.update { it.copy(isLoading = true) }
            val request = repository.postHeavyVehicleFuel(token, data)
            request.enqueue(
                object : Callback<HeavyVehicleFuelResponse> {
                    override fun onResponse(
                        call: Call<HeavyVehicleFuelResponse>,
                        response: Response<HeavyVehicleFuelResponse>
                    ) {
                        _state.update { it.copy(isLoading = false) }
                        when (response.code()) {
                            201 -> _state.update {
                                it.copy(
                                    isPostSuccessful = true,
                                    notificationMessage = "Data berhasil disimpan!"
                                )
                            }

                            else -> viewModelScope.launch {
                                repository.storeHeavyVehicleFuel(heavyVehicleEntity)
                                _state.update { it.copy(notificationMessage = "Data berhasil disimpan!") }
                            }
                        }
                    }

                    override fun onFailure(call: Call<HeavyVehicleFuelResponse>, t: Throwable) {
                        viewModelScope.launch {
                            repository.storeHeavyVehicleFuel(heavyVehicleEntity)
                            _state.update {
                                it.copy(
                                    notificationMessage = "Data berhasil disimpan!",
                                    isPostSuccessful = true,
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            )
        } else viewModelScope.launch {
            repository.storeHeavyVehicleFuel(heavyVehicleEntity)
            _state.update {
                it.copy(
                    notificationMessage = "Data berhasil disimpan!",
                    isPostSuccessful = true
                )
            }
        }
    }

    // state related methods
    private fun onNavigateForm(scanOptions: ScanOptions) {
        _state.update { it.copy(currentlyScanning = scanOptions) }
    }

    private fun onHeavyVehicleIdRegistered(vHId: String, connectionStatus: Status) {
        if (connectionStatus == Status.Available) {
            checkHeavyVehicleId(vHId)
        } else viewModelScope.launch {
            _state.update { it.copy(heavyVehicleId = vHId) }
            delay(1000)
            _state.update { it.copy(currentlyScanning = ScanOptions.Driver) }
        }
    }

    private fun onDriverIdRegistered(driverId: String, connectionStatus: Status) {
        if (connectionStatus == Status.Available) {
            checkDriverId(driverId)
        } else viewModelScope.launch {
            _state.update { it.copy(driverId = driverId) }
            delay(1000)
            _state.update { it.copy(currentlyScanning = ScanOptions.Pos) }
        }
    }

    private fun onStationIdRegistered(stationId: String, connectionStatus: Status) {
        if (connectionStatus == Status.Available) {
            checkStationId(stationId)
        } else viewModelScope.launch {
            _state.update { it.copy(stationId = stationId) }
            delay(1000)
            _state.update { it.copy(currentlyScanning = ScanOptions.Volume) }
        }
    }

    private fun onVolumeRegistered(volume: Double?) {
        if (volume == null) {
            _state.update { it.copy(notificationMessage = "Maaf, Qr invalid.") }
        } else viewModelScope.launch {
            _state.update { it.copy(volume = volume) }
            delay(1000)
            _state.update { it.copy(currentlyScanning = ScanOptions.None) }
        }
    }

    private fun onHourMeterChange(odometer: String) {
        _state.update {
            it.copy(hourmeter = odometer)
        }
    }

    private fun onClearHourMeter() {
        _state.update {
            it.copy(hourmeter = "")
        }
    }

    private fun onRemarksChange(remarks: String) {
        _state.update {
            it.copy(remarks = remarks)
        }
    }

    private fun onClearRemarks() {
        _state.update {
            it.copy(remarks = "")
        }
    }

    private fun onNotificationClear() {
        _state.update {
            it.copy(notificationMessage = "")
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun saveHeavyVehicleFuelTransaction(connectionStatus: Status) {
        if (state.value.hourmeter.isBlank()) {
            _state.update {
                it.copy(
                    hourmeterErrorState = ErrorTextField(
                        isError = !it.hourmeterErrorState.isError,
                        errorMessage = "Hourmeter tidak bolah kosong."
                    )
                )
            }
        } else {
            val data = FuelHeavyVehicleEntity(
                heavyVehicleId = state.value.heavyVehicleId,
                driverId = state.value.driverId,
                stationId = state.value.stationId,
                gasOperatorId = state.value.userId,
                volume = state.value.volume,
                hourmeter = state.value.hourmeter.commaToPeriod().toDouble(),
                remarks = state.value.remarks,
                date = LocalDateTime.now().toString()
            )
            postHeavyVehicleFuel(data, connectionStatus)
        }
    }

    // event handler
    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: HeavyVehicleFuelQrScreenEvent) {
        when (event) {
            is HeavyVehicleFuelQrScreenEvent.OnNavigateForm -> onNavigateForm(event.scanOptions)

            is HeavyVehicleFuelQrScreenEvent.OnHeavyVehicleIdRegistered -> onHeavyVehicleIdRegistered(event.vHId, event.connectionStatus)

            is HeavyVehicleFuelQrScreenEvent.OnDriverIdRegistered -> onDriverIdRegistered(event.driverId, event.connectionStatus)

            is HeavyVehicleFuelQrScreenEvent.OnStationIdRegistered -> onStationIdRegistered(event.stationId, event.connectionStatus)

            is HeavyVehicleFuelQrScreenEvent.OnVolumeRegistered -> onVolumeRegistered(event.volume)

            is HeavyVehicleFuelQrScreenEvent.OnHourmeterChange -> onHourMeterChange(event.odometer)

            HeavyVehicleFuelQrScreenEvent.OnClearHourmeter -> onClearHourMeter()

            is HeavyVehicleFuelQrScreenEvent.OnRemarksChange -> onRemarksChange(event.remarks)

            HeavyVehicleFuelQrScreenEvent.OnClearRemarks -> onClearRemarks()

            HeavyVehicleFuelQrScreenEvent.NotificationClear -> onNotificationClear()

            is HeavyVehicleFuelQrScreenEvent.SaveHeavyVehicleFuelTransaction -> saveHeavyVehicleFuelTransaction(event.connectionStatus)
        }
    }

}