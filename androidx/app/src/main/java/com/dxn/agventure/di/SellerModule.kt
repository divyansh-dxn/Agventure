package com.dxn.agventure.di



import com.dxn.agventure.features.seller.data.RepositoryImpl
import com.dxn.agventure.features.seller.domain.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SellerModule {

    @Provides
    fun provideAuth() = Firebase.auth

    @Provides
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Provides
    fun provideRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): Repository {
        return RepositoryImpl(firestore, auth)
    }

}
