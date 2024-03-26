package com.system.hasilkarya.core.repositories

import com.system.hasilkarya.core.entities.FuelTruckEntity
import com.system.hasilkarya.core.network.ApiServices
import com.system.hasilkarya.core.preferences.AppPreferences
import com.system.hasilkarya.fuel.data.TruckFuelLogRequest
import com.system.hasilkarya.fuel.data.TruckFuelRequest
import javax.inject.Inject

class FuelRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val preferences: AppPreferences,
    private val dao: FuelDao
){

    fun getUserId() = preferences.getUserId()
    fun getToken() = preferences.getToken()
    suspend fun saveFuel(fuelTruckEntity: FuelTruckEntity) = dao.saveFuel(fuelTruckEntity)
    suspend fun deleteFuel(fuelTruckEntity: FuelTruckEntity) = dao.deleteFuel(fuelTruckEntity)
    fun getFuels() = dao.getFuels()
    fun postFuels(token: String, truckFuelRequest: TruckFuelRequest) = apiServices.postFuelTruck(token, truckFuelRequest)
    fun checkDriverId(token: String, driverId: String) = apiServices.checkDriverId(driverId, token)
    fun checkTruckId(token: String, truckId: String) = apiServices.checkTruckId(truckId, token)
    fun checkStationId(token: String, stationId: String) = apiServices.checkStationId(stationId, token)
    fun postToLog(token: String, truckFuelRequest: TruckFuelLogRequest) = apiServices.postFuelLog(token, truckFuelRequest)
}