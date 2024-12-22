package com.example.absenkuy.data.response

import com.google.gson.annotations.SerializedName

data class JadwalResponse(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("Kode_MK")
	val kodeMK: String? = null,

	@field:SerializedName("kelas")
	val kelas: String? = null,

	@field:SerializedName("hariKuliah")
	val hariKuliah: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("jamMulai")
	val jamMulai: String? = null,

	@field:SerializedName("jamSelesai")
	val jamSelesai: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
