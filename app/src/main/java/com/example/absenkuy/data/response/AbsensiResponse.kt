package com.example.absenkuy.data.response

import com.google.gson.annotations.SerializedName

data class AbsensiResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("NIM")
	val nIM: String? = null,

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("lokasi")
	val lokasi: String? = null,

	@field:SerializedName("waktu")
	val waktu: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("kode_JK")
	val kodeJK: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
