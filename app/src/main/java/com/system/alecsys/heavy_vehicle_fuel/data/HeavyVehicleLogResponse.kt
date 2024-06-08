package com.system.alecsys.heavy_vehicle_fuel.data

import com.google.gson.annotations.SerializedName

data class HeavyVehicleLogResponse(

	@field:SerializedName("data")
	val data: Data1,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Data1(

	@field:SerializedName("date")
	val date: Any,

	@field:SerializedName("truck_id")
	val truckId: Any,

	@field:SerializedName("driver_id")
	val driverId: String,

	@field:SerializedName("code")
	val code: Any,

	@field:SerializedName("error_log")
	val errorLog: String,

	@field:SerializedName("observation_ratio_percentage")
	val observationRatioPercentage: Any,

	@field:SerializedName("station_id")
	val stationId: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("checker_id")
	val checkerId: Any,

	@field:SerializedName("solid_ratio")
	val solidRatio: Any,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("truck_capacity")
	val truckCapacity: Any,

	@field:SerializedName("remarks")
	val remarks: String
)
