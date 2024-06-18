package com.system.alecsys.core.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stations")
data class StationEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name", defaultValue = "")
    val name: String = "",

    @ColumnInfo(name = "regency", defaultValue = "")
    val regency: String = "",

    @ColumnInfo(name = "province", defaultValue = "")
    val province: String = "",

    @ColumnInfo(name = "station_id", defaultValue = "")
    val stationId: String = "",

    @ColumnInfo(name = "is_active", defaultValue = "false")
    val isActive: Boolean = false,
)