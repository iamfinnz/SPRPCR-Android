package com.example.aplikasipeminjamanruangan.di

import com.example.aplikasipeminjamanruangan.data.AppRepository
import com.example.aplikasipeminjamanruangan.domain.repository.IAppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractModules {
    @Binds
    abstract fun provideIAppRepository(appRepository: AppRepository): IAppRepository

}