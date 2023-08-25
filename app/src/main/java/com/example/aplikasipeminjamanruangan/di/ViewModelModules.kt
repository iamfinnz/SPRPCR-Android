package com.example.aplikasipeminjamanruangan.di

import com.example.aplikasipeminjamanruangan.domain.usecase.AppInteractor
import com.example.aplikasipeminjamanruangan.domain.usecase.IAppUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModules {
    @Binds
    @ViewModelScoped
    abstract fun provideIAppUseCase(appInteractor: AppInteractor): IAppUseCase
}