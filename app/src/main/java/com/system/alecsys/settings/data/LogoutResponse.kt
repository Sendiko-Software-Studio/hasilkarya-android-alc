package com.system.alecsys.settings.data

import com.google.gson.annotations.SerializedName

data class LogoutResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)
