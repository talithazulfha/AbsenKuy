package com.example.absenkuy.data.retrofit


import com.example.absenkuy.data.request.AbsensiRequest
import com.example.absenkuy.data.response.AbsensiResponse
import com.example.absenkuy.data.response.HomeResponse
import com.example.absenkuy.data.response.LoginResponse
import com.example.absenkuy.data.response.MatkulResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("NIM") NIM: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("home/{NIM}")
    suspend fun getHome(
        @Path("NIM") NIM: String
    ): HomeResponse

    @GET("jadwal/{NIM}")
    suspend fun getAll(
        @Path("NIM") NIM: String
    ): MatkulResponse

    @POST("absensi/{NIM}")
    suspend fun absensi(
        @Path("NIM") NIM: String,
        @Body absensiRequest: AbsensiRequest
    ): AbsensiResponse
}
