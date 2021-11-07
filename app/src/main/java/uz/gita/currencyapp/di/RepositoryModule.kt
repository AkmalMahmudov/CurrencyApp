package uz.gita.currencyapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.currencyapp.model.data.ApiService
import uz.gita.currencyapp.model.repository.MainRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun getRepository(service: ApiService): MainRepository = MainRepository(service)
}