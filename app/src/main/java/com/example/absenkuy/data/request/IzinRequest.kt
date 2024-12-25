package com.example.absenkuy.data.request

data class IzinRequest(
    val NIM: String,
    val keterangan: String,
    val bukti: ByteArray // File dikirim sebagai byte array
)
