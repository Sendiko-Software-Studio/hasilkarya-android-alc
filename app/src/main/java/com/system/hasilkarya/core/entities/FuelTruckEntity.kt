package com.system.hasilkarya.core.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fuel_truck")
data class FuelTruckEntity(

    @PrimaryKey
    val truckId: String = "",
    val driverId: String = "",
    val stationId: String = "",
    val userId: String = "",
    val volume: Double = 0.0,
    val odometer: Double = 0.0,
    val remarks: String = "",

    @ColumnInfo(defaultValue = "0")
    val date: String = "",

)
