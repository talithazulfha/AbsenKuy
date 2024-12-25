package com.example.absenkuy.data.response

data class FeedbackRequest(
    val NIM: String,
    val kode_jk: String,
    val ratingM: Int,
    val ratingD: Int,
    val komentarM: String,
    val komentarD: String,
    val tanggal: Long
)

data class FeedbackResponse(
    val message: String,
    val data: FeedbackData
)

data class FeedbackData(
    val id: Int,
    val NIM: String,
    val kode_jk: String,
    val ratingM: Int,
    val ratingD: Int,
    val komentarM: String,
    val komentarD: String,
    val tanggal: String,
    val createdAt: String,
    val updatedAt: String
)