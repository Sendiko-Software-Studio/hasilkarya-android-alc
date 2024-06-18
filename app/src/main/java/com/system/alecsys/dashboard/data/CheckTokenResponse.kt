package com.system.alecsys.dashboard.data

import com.google.gson.annotations.SerializedName

data class CheckTokenResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class RolesItem(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("pivot")
	val pivot: Pivot,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("guard_name")
	val guardName: String
)

data class Pivot(

	@field:SerializedName("role_id")
	val roleId: String,

	@field:SerializedName("model_type")
	val modelType: String,

	@field:SerializedName("model_id")
	val modelId: String
)

data class Checker(

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("is_active")
	val isActive: Boolean,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("deleted_at")
	val deletedAt: Any
)

data class Data(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("roles")
	val roles: List<RolesItem>,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("checker")
	val checker: Checker,

	@field:SerializedName("email")
	val email: String
)
