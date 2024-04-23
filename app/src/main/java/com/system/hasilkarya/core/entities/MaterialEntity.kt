package com.system.hasilkarya.core.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "material")
data class MaterialEntity(
    @PrimaryKey
    val driverId: String,
    val truckId: String,
    val stationId: String,
    val checkerId: String,
    val ratio: Double,
    val remarks: String,

    @ColumnInfo(defaultValue = "0")
    val date: String = ""
)
