package com.system.alecsys.material.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.alecsys.core.entities.MaterialEntity
import com.system.alecsys.core.preferences.AppPreferences
import com.system.alecsys.core.repositories.material.MaterialRepository
import com.system.alecsys.core.repositories.station.StationRepository
import com.system.alecsys.core.ui.utils.ErrorTextField
import com.system.alecsys.core.ui.utils.FailedRequest
import com.system.alecsys.core.utils.commaToPeriod
import com.system.alecsys.dashboard.presentation.component.ScanOptions
import com.system.alecsys.dashboard.presentation.component.ScanOptions.None
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class MaterialQrScreenViewModel @Inject constructor(
    private val repository: MaterialRepository,
    private val stationRepository: StationRepository,
    preferences: AppPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(MaterialQrScreenState())
    private val _token = preferences.getToken()
    private val _userId = preferences.getUserId()
    private val _station = stationRepository.getAllStations()
    val state = combine(
        _token, _userId, _station, _state
    ) { token, userId, station, state ->
        state.copy(
            token = token,
            userId = userId,
            stationId = station.first().stationId,
            stationName = station.first().name
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), MaterialQrScreenState())

    private fun postMaterial(materialEntity: MaterialEntity) {
        viewModelScope.launch { repository.saveMaterial(materialEntity) }
        _state.update {
            it.copy(
                notificationMessage = "Data berhasil tersimpan!",
                isLoading = false,
                isPostSuccessful = true
            )
        }
    }

    // state related methods
    private fun onTruckIdRegistered(truckId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(truckId = truckId)
            }
            delay(1000)
            _state.update {
                it.copy(currentlyScanning = None)
            }
        }
    }

    private fun onVolumeChange(volume: String) {
        _state.update {
            it.copy(materialVolume = volume)
        }
    }

    private fun onRemarksChange(remarks: String) {
        _state.update {
            it.copy(remarks = remarks)
        }
    }

    private fun onClearNotification() {
        _state.update {
            it.copy(notificationMessage = "", isRequestFailed = FailedRequest())
        }
    }

    private fun onClearRemarks() {
        _state.update {
            it.copy(remarks = "")
        }
    }

    private fun onNavigateForm(scanOptions: ScanOptions) {
        _state.update {
            it.copy(currentlyScanning = scanOptions)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onSaveMaterial() {
        if (state.value.materialVolume.isBlank()) {
            _state.update {
                it.copy(
                    materialVolumeErrorState = ErrorTextField(
                        isError = !it.materialVolumeErrorState.isError,
                        errorMessage = "Volume Material tidak boleh kosong."
                    )
                )
            }
        } else {
            val data = MaterialEntity(
                driverId = state.value.driverId,
                truckId = state.value.truckId,
                stationId = state.value.stationId,
                ratio = state.value.materialVolume.commaToPeriod().toDouble(),
                remarks = state.value.remarks,
                checkerId = state.value.userId,
                date = LocalDateTime.now().toString(),
            )
            postMaterial(data)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: MaterialQrScreenEvent) {
        when (event) {
            is MaterialQrScreenEvent.OnTruckIdRegistered -> onTruckIdRegistered(event.truckId)

            is MaterialQrScreenEvent.OnVolumeChange -> onVolumeChange(event.volume)

            is MaterialQrScreenEvent.OnNewRemarks -> onRemarksChange(event.remarks)

            is MaterialQrScreenEvent.SaveMaterial -> onSaveMaterial()

            MaterialQrScreenEvent.OnClearNotification -> onClearNotification()

            is MaterialQrScreenEvent.OnClearRemarks -> onClearRemarks()

            is MaterialQrScreenEvent.OnNavigateForm -> onNavigateForm(event.scanOptions)
        }
    }
}