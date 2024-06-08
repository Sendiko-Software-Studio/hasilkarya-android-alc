package com.system.alecsys.core.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "material")
data class MaterialEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(defaultValue = "0")
    val id: Int = 0,
    val truckId: String,
    val driverId: String,
    val stationId: String,
    val checkerId: String,
    val ratio: Double,
    val remarks: String,

    @ColumnInfo(defaultValue = "0")
    val date: String = ""
)
