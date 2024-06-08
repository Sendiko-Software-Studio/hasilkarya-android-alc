package com.system.alecsys.heavy_vehicle_fuel.data

import com.google.gson.annotations.SerializedName

data class HeavyVehicleFuelLogRequest(
    @field:SerializedName("volume")
    val volume: Any,

    @field:SerializedName("heavy_vehicle_id")
    val heavyVehicleId: String,

    @field:SerializedName("driver_id")
    val driverId: String,

    @field:SerializedName("hourmeter")
    val hourmeter: Any,

    @field:SerializedName("error_log")
    val errorLog: String,

    @field:SerializedName("station_id")
    val stationId: String,

    @field:SerializedName("gas_operator_id")
    val gasOperatorId: String,

    @field:SerializedName("remarks")
    val remarks: String,

    @field:SerializedName("date")
    val date: String,
)
