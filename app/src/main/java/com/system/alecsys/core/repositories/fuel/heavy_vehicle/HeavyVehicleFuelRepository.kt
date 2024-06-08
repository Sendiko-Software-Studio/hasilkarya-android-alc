package com.system.alecsys.core.repositories.fuel.heavy_vehicle

import com.system.alecsys.core.entities.FuelHeavyVehicleEntity
import com.system.alecsys.core.network.ApiServices
import com.system.alecsys.core.preferences.AppPreferences
import com.system.alecsys.heavy_vehicle_fuel.data.HeavyVehicleFuelLogRequest
import com.system.alecsys.heavy_vehicle_fuel.data.HeavyVehicleFuelRequest
import javax.inject.Inject

class HeavyVehicleFuelRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val preferences: AppPreferences,
    private val dao: HeavyVehicleDao
) {

    fun getUserId() = preferences.getUserId()
    fun getToken() = preferences.getToken()
    suspend fun storeHeavyVehicleFuel(fuelHeavyVehicleEntity: FuelHeavyVehicleEntity) = dao.storeHeavyVehicleFuel(fuelHeavyVehicleEntity)
    suspend fun deleteHeavyVehicleFuel(fuelHeavyVehicleEntity: FuelHeavyVehicleEntity) = dao.deleteHeavyVehicleFuel(fuelHeavyVehicleEntity)
    fun getHeavyVehicleFuels() = dao.getHeavyVehicleFuels()
    fun checkHeavyVehicleId(token: String, heavyVehicleId: String) = apiServices.checkHeavyVehicleId(heavyVehicleId, token)
    fun checkDriverId(token: String, driverId: String) = apiServices.checkDriverId(driverId, token)
    fun checkStationId(token: String, stationId: String) = apiServices.checkGasStationId(stationId, token)
    fun postHeavyVehicleFuel(token: String, heavyVehicleFuelRequest: HeavyVehicleFuelRequest) = apiServices.postFuelHeavyVehicle(token, heavyVehicleFuelRequest)
    fun postHeavyVehicleFuelLog(token: String, heavyVehicleFuelLogRequest: HeavyVehicleFuelLogRequest) = apiServices.postFuelHeavyVehicleLog(token, heavyVehicleFuelLogRequest)
}