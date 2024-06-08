package com.system.alecsys.heavy_vehicle_fuel.data

import com.google.gson.annotations.SerializedName


data class CheckHeavyVehicleIdResponse(
    @field:SerializedName("data")
    val data: Any,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)
