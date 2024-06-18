package com.system.alecsys.dashboard.data

import com.google.gson.annotations.SerializedName

data class CheckDriverIdResponse(

	@field:SerializedName("data")
	val data: Any,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)
