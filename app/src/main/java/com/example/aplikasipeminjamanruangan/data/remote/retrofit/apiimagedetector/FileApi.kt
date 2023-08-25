package com.example.aplikasipeminjamanruangan.data.remote.retrofit.apiimagedetector

import com.example.aplikasipeminjamanruangan.domain.model.RetrofitImageModel
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileApi {
    @Multipart
    @POST("/predict")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): RetrofitImageModel
}