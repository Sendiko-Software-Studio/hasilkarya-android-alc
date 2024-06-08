package com.system.alecsys.truck_fuel.data

import com.google.gson.annotations.SerializedName

data class TruckFuelRequest(

	@field:SerializedName("volume")
	val volume: Any,

	@field:SerializedName("truck_id")
	val truckId: String,

	@field:SerializedName("driver_id")
	val driverId: String,

	@field:SerializedName("odometer")
	val odometer: Any,

	@field:SerializedName("fuel_station_id")
	val fuelStationId: String,

	@field:SerializedName("fuel_operator_id")
	val fuelOperatorId: String,

	@field:SerializedName("remarks")
	val remarks: String,

	@field:SerializedName("date")
	val date: String,
)
