package com.system.hasilkarya.heavy_vehicle_fuel.data

import com.google.gson.annotations.SerializedName

data class HeavyVehicleFuelRequest(

	@field:SerializedName("volume")
	val volume: Any,

	@field:SerializedName("driver_id")
	val driverId: String,

	@field:SerializedName("hourmeter")
	val hourmeter: Any,

	@field:SerializedName("station_id")
	val stationId: String,

	@field:SerializedName("fuel_operator_id")
	val fuelOperatorId: String,

	@field:SerializedName("heavy_vehicle_id")
	val heavyVehicleId: String,

	@field:SerializedName("remarks")
	val remarks: String,

	@field:SerializedName("date")
	val date: String,
)
