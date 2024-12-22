package com.example.absenkuy.data.response

import com.google.gson.annotations.SerializedName

data class MatkulResponse(

	@field:SerializedName("mataKuliah")
	val mataKuliah: List<String?>? = null
)
