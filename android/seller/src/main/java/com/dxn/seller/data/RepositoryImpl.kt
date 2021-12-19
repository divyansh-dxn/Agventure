package com.dxn.seller.data

import com.dxn.data.models.CatalogueProduct
import com.dxn.seller.domain.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class RepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : Repository {

    @ExperimentalCoroutinesApi
    override fun getProducts(sellerId: String): Flow<List<CatalogueProduct>> =
        callbackFlow {
            val listener =
                firestore.collection("products_collection").whereEqualTo("sellerId", auth.uid!!)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            throw error
                        } else {
                            value?.let {
                                val products = it.toObjects(CatalogueProduct::class.java)
                                trySend(products)
                            }
                        }
                    }
            listener.remove()
        }

    override suspend fun addProducts(product: CatalogueProduct) {
        firestore.collection("products_collection").document().set(product)
    }

}