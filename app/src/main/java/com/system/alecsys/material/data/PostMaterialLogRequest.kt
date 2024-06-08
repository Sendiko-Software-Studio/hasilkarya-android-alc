package com.system.alecsys.material.data

import com.google.gson.annotations.SerializedName

data class PostMaterialLogRequest(

	@field:SerializedName("truck_id")
	val truckId: String,

	@field:SerializedName("driver_id")
	val driverId: String,

	@field:SerializedName("error_log")
	val errorLog: String,

	@field:SerializedName("station_id")
	val stationId: String,

	@field:SerializedName("checker_id")
	val checkerId: String,

	@field:SerializedName("remarks")
	val remarks: String,

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("observation_ratio")
	val ratio: String
)
