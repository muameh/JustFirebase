package com.mehmetbaloglu.firebasecalismasi_dsncepaylas.di


import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.data.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providePostRepository(): PostRepository {
        return PostRepository()
    }

}

