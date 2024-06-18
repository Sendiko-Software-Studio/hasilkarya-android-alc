package com.system.alecsys.core.repositories.fuel.truck

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.system.alecsys.core.entities.FuelTruckEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TruckFuelDao {

    @Upsert
    suspend fun saveFuel(fuelTruckEntity: FuelTruckEntity)

    @Query("SELECT * FROM fuel_truck")
    fun getFuels(): Flow<List<FuelTruckEntity>>

    @Delete
    suspend fun deleteFuel(fuelTruckEntity: FuelTruckEntity)
}