package com.example.absenkuy.data.response

import com.google.gson.annotations.SerializedName

data class HomeResponse(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("departemen")
	val departemen: String? = null
)
