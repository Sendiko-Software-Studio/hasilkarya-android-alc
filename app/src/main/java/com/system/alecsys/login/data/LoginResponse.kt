package com.system.alecsys.login.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("user_data")
	val userData: UserData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("token")
	val token: String
)

data class UserData(

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("permissions")
	val permissions: List<String>,

	@field:SerializedName("fuel_operator_id")
	val fuelOperatorId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String
)
