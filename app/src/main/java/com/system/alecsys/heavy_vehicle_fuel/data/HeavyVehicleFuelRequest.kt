package com.system.alecsys.heavy_vehicle_fuel.data

import com.google.gson.annotations.SerializedName

data class HeavyVehicleFuelRequest(

	@field:SerializedName("volume")
	val volume: Any,

	@field:SerializedName("driver_id")
	val driverId: String,

	@field:SerializedName("hourmeter")
	val hourmeter: Any,

	@field:SerializedName("fuel_station_id")
	val fuelStationId: String,

	@field:SerializedName("fuel_operator_id")
	val fuelOperatorId: String,

	@field:SerializedName("heavy_vehicle_id")
	val heavyVehicleId: String,

	@field:SerializedName("remarks")
	val remarks: String,

	@field:SerializedName("date")
	val date: String,
)
