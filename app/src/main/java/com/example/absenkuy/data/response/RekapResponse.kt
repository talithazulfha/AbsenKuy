package com.example.absenkuy.data.response

import com.google.gson.annotations.SerializedName

data class RekapResponse(
    @field:SerializedName("kehadiranList")
    val kehadiranList: List<Kehadiran>
)

data class Kehadiran(
    @field:SerializedName("mataKuliah")
    val mataKuliah: String,

    @field:SerializedName("kodeMK")
    val kodeMK: String,

    @field:SerializedName("hadir")
    val hadir: Int,

    @field:SerializedName("alfa")
    val alfa: Int,

    @field:SerializedName("izin")
    val izin: Int
)
