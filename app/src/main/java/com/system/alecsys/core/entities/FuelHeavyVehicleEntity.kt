package com.system.alecsys.core.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fuel_heavy_vehicle")
data class FuelHeavyVehicleEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(defaultValue = "0")
    val id: Int = 0,
    val heavyVehicleId: String,
    val stationId: String,
    val driverId: String,
    val gasOperatorId: String,
    val volume: Double,
    val hourmeter: Double,
    val remarks: String,

    @ColumnInfo(defaultValue = "0")
    val date: String
)
