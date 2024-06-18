package com.system.alecsys.core.repositories

import androidx.room.Database
import androidx.room.RoomDatabase
import com.system.alecsys.core.entities.FuelHeavyVehicleEntity
import com.system.alecsys.core.entities.FuelTruckEntity
import com.system.alecsys.core.entities.StationEntity
import com.system.alecsys.core.repositories.fuel.heavy_vehicle.HeavyVehicleDao
import com.system.alecsys.core.repositories.fuel.truck.TruckFuelDao

@Database(
    entities = [
        FuelTruckEntity::class,
        FuelHeavyVehicleEntity::class,
        StationEntity::class
    ],
    version = 7
)
abstract class AppDatabase : RoomDatabase() {
    abstract val truckFuelDao: TruckFuelDao
    abstract val heavyVehicleDao: HeavyVehicleDao
}
