package com.dxn.agventure.features.consumer.di

import com.dxn.agventure.features.consumer.data.RepositoryImpl
import com.dxn.agventure.features.consumer.domain.Repository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object SellerModule {


    @Provides
    fun provideRepository(firestore: FirebaseFirestore): Repository {
        return RepositoryImpl(firestore)
    }

}
