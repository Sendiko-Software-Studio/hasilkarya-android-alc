package com.system.alecsys.material.data

import com.google.gson.annotations.SerializedName

data class PostToLogResponse(

	@field:SerializedName("data")
	val data: Data1? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("date")
	val date: Any? = null,

	@field:SerializedName("truck_id")
	val truckId: String? = null,

	@field:SerializedName("driver_id")
	val driverId: String? = null,

	@field:SerializedName("code")
	val code: Any? = null,

	@field:SerializedName("error_log")
	val errorLog: String? = null,

	@field:SerializedName("observation_ratio_percentage")
	val observationRatioPercentage: Any? = null,

	@field:SerializedName("station_id")
	val stationId: String? = null,

	@field:SerializedName("solid_ratio")
	val solidRatio: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("checker_id")
	val checkerId: String? = null,

	@field:SerializedName("truck_capacity")
	val truckCapacity: Any? = null,

	@field:SerializedName("remarks")
	val remarks: Any? = null
)
