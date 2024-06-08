package com.system.alecsys.station.data

import com.google.gson.annotations.SerializedName

data class GetStationResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("is_active")
	val isActive: Boolean,

	@field:SerializedName("province")
	val province: String,

	@field:SerializedName("material")
	val material: Any,

	@field:SerializedName("subdistrict")
	val subdistrict: String,

	@field:SerializedName("district")
	val district: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("regency")
	val regency: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("category")
	val category: String
)
