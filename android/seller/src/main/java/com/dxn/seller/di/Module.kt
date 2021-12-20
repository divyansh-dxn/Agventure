package com.dxn.seller.di

import android.app.Application
import com.dxn.data.models.User
import com.dxn.seller.data.RepositoryImpl
import com.dxn.seller.domain.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.tasks.await

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun provideAuth() = Firebase.auth

    @Provides
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Provides
    fun provideRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): Repository {
        return RepositoryImpl(firestore, auth)
    }

}