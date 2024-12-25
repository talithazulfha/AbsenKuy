package com.example.absenkuy.data.response

import com.google.gson.annotations.SerializedName

data class MatkulResponse(

	@field:SerializedName("mataKuliah")
	val mataKuliah: List<String?>? = null,

	val id: String,
	val namaMatkul: String,
	val sks: Int,
	val createdAt: String,
	val updatedAt: String
)
