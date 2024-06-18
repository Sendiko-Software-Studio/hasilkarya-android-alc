package com.system.alecsys.truck_fuel.data

import com.google.gson.annotations.SerializedName

data class TruckFuelResponse(

    @field:SerializedName("data")
	val data: Data1,

    @field:SerializedName("success")
	val success: Boolean,

    @field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("volume")
	val volume: Any,

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("odometer")
	val odometer: Any,

	@field:SerializedName("hourmeter")
	val hourmeter: Any,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("fuel_type")
	val fuelType: String,

	@field:SerializedName("remarks")
	val remarks: String
)
