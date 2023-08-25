package com.example.aplikasipeminjamanruangan.data.remote.retrofit.apipcr

import com.example.aplikasipeminjamanruangan.domain.model.RetrofitImageModel
import com.example.aplikasipeminjamanruangan.domain.model.RetrofitPcrModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FilePcrApi {
    @GET("/api/akademik-mahasiswa")
    suspend fun validateNim(
        @Query("apikey") apikey: String,
        @Query("collection") collection: String,
        @Query("nim") nim: String
    ): RetrofitPcrModel
}