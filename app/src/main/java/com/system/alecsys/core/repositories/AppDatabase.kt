package com.system.alecsys.core.repositories

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.system.alecsys.core.entities.FuelHeavyVehicleEntity
import com.system.alecsys.core.entities.FuelTruckEntity
import com.system.alecsys.core.entities.MaterialEntity
import com.system.alecsys.core.entities.StationEntity
import com.system.alecsys.core.repositories.fuel.heavy_vehicle.HeavyVehicleDao
import com.system.alecsys.core.repositories.fuel.truck.TruckFuelDao
import com.system.alecsys.core.repositories.material.MaterialDao
import com.system.alecsys.core.repositories.station.StationDao

@Database(
    entities = [
        MaterialEntity::class,
        FuelTruckEntity::class,
        FuelHeavyVehicleEntity::class,
        StationEntity::class
    ],
    version = 7,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val materialDao: MaterialDao
    abstract val truckFuelDao: TruckFuelDao
    abstract val heavyVehicleDao: HeavyVehicleDao
    abstract val stationDao: StationDao
}

@DeleteTable.Entries(
    DeleteTable(tableName = "gas")
)
class Migration23 : AutoMigrationSpec