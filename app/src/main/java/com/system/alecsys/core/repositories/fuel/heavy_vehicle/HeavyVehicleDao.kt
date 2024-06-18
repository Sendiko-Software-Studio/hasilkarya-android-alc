package com.system.alecsys.core.repositories.fuel.heavy_vehicle

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.system.alecsys.core.entities.FuelHeavyVehicleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HeavyVehicleDao {

    @Upsert
    suspend fun storeHeavyVehicleFuel(fuelHeavyVehicleEntity: FuelHeavyVehicleEntity)

    @Query("SELECT * FROM fuel_heavy_vehicle")
    fun getHeavyVehicleFuels(): Flow<List<FuelHeavyVehicleEntity>>

    @Delete
    suspend fun deleteHeavyVehicleFuel(fuelHeavyVehicleEntity: FuelHeavyVehicleEntity)
}