package com.example.aplikasipeminjamanruangan.di

import com.example.aplikasipeminjamanruangan.presentation.utils.DB_DOSEN
import com.example.aplikasipeminjamanruangan.presentation.utils.DB_MATAKULIAH
import com.example.aplikasipeminjamanruangan.presentation.utils.DB_PEMINJAMAN
import com.example.aplikasipeminjamanruangan.presentation.utils.DB_PENGAJUAN
import com.example.aplikasipeminjamanruangan.presentation.utils.DB_ROOMS
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RealtimeDBModule {
    @Provides
    @Singleton
    @Named(DB_ROOMS)
    fun provideRealtimeDBRoomsRef(): DatabaseReference = Firebase.database.getReference(DB_ROOMS)

    @Provides
    @Singleton
    @Named(DB_PENGAJUAN)
    fun provideRealtimeDBPengajuanRef(): DatabaseReference = Firebase.database.getReference(DB_PENGAJUAN)

    @Provides
    @Singleton
    @Named(DB_PEMINJAMAN)
    fun provideRealtimeDBPeminjamanRef(): DatabaseReference = Firebase.database.getReference(
        DB_PEMINJAMAN)

    @Provides
    @Singleton
    @Named(DB_MATAKULIAH)
    fun provideRealtimeDBMataKuliahRef(): DatabaseReference = Firebase.database.getReference(
        DB_MATAKULIAH)

    @Provides
    @Singleton
    @Named(DB_DOSEN)
    fun provideRealtimeDBDosenRef(): DatabaseReference = Firebase.database.getReference(
        DB_DOSEN)
}