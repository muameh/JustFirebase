package com.mehmetbaloglu.firebasecalismasi_dsncepaylas.di


import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.data.repository.PostRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

