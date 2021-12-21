package com.dxn.agventure.features.consumer.data

import com.dxn.agventure.features.consumer.domain.Repository
import com.dxn.data.models.CatalogueProduct
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RepositoryImpl(
    private val fireStore: FirebaseFirestore
) : Repository {
    @ExperimentalCoroutinesApi
    override fun getProducts(): Flow<List<CatalogueProduct>> = callbackFlow {
        val listener =
            fireStore.collection("products_collection").addSnapshotListener { value, error ->
                if (error != null) {
                    throw error
                }
                value?.let {
                    val products = it.toObjects(CatalogueProduct::class.java)
                    trySend(products)
                }
            }
        awaitClose { listener.remove() }
    }
}