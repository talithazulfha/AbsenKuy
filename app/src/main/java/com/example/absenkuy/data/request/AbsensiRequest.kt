package com.example.absenkuy.data.request

import com.google.gson.annotations.SerializedName

data class AbsensiRequest(

	@field:SerializedName("NIM")
	val nIM: String? = null,

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("lokasi")
	val lokasi: String? = null,

	@field:SerializedName("waktu")
	val waktu: String? = null,

	@field:SerializedName("kode_JK")
	val kodeJK: String? = "hadir"
)
