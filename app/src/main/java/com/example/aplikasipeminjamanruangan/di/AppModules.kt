package com.example.aplikasipeminjamanruangan.di

import com.example.aplikasipeminjamanruangan.data.remote.firebase.RealtimeDB
import com.example.aplikasipeminjamanruangan.presentation.utils.BASE_URL_IMAGE_DETECTION
import com.example.aplikasipeminjamanruangan.presentation.utils.BASE_URL_PCR
import com.example.aplikasipeminjamanruangan.presentation.utils.DB_DOSEN
import com.example.aplikasipeminjamanruangan.presentation.utils.DB_MATAKULIAH
import com.example.aplikasipeminjamanruangan.presentation.utils.DB_PEMINJAMAN
import com.example.aplikasipeminjamanruangan.presentation.utils.DB_PENGAJUAN
import com.example.aplikasipeminjamanruangan.presentation.utils.DB_ROOMS
import com.example.aplikasipeminjamanruangan.presentation.utils.URL_IMAGE_DETECTION
import com.example.aplikasipeminjamanruangan.presentation.utils.URL_PCR
import com.google.firebase.database.DatabaseReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModules {
    @Provides
    @Singleton
    fun provideRepository(
        @Named(DB_ROOMS) referenceDbRooms: DatabaseReference,
        @Named(DB_PENGAJUAN) referenceDbPengajuan: DatabaseReference,
        @Named(DB_PEMINJAMAN) referenceDbPeminjaman: DatabaseReference,
        @Named(DB_MATAKULIAH) referenceDbMataKuliah: DatabaseReference,
        @Named(DB_DOSEN) referenceDbDosen: DatabaseReference
    ): RealtimeDB =
        RealtimeDB(
            dbRoomsReference = referenceDbRooms,
            dbPengajuanReference = referenceDbPengajuan,
            dbPeminjamanReference = referenceDbPeminjaman,
            dbMataKuliahReference = referenceDbMataKuliah,
            dbDosenReference = referenceDbDosen
        )

    @Provides
    @Singleton
    @Named(URL_IMAGE_DETECTION)
    fun provideRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL_IMAGE_DETECTION)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named(URL_PCR)
    fun provideRetrofitInstance2(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL_PCR)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
}