package com.dxn.agventure.features.seller.data

import android.util.Log
import com.dxn.agventure.features.seller.domain.Repository
import com.dxn.data.models.CatalogueProduct
import com.dxn.data.models.Product
import com.dxn.data.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.lang.NullPointerException

class RepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : Repository {

    @ExperimentalCoroutinesApi
    override fun getProducts(sellerId: String): Flow<List<CatalogueProduct>> =
        callbackFlow {
            val listener =
                firestore.collection("products_collection").whereEqualTo("sellerId", sellerId)
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
            awaitClose { listener.remove() }
        }

    override suspend fun addProducts(sellerId: String,product: CatalogueProduct) {
        try {
            val productDocument =
                firestore.collection("products_collection")
                    .whereEqualTo("productId", product.productId)
                    .whereEqualTo("sellerId",sellerId)
                    .get().await().documents[0]
            val dbProduct = productDocument.toObject(CatalogueProduct::class.java)!!
            firestore.collection("products_collection").document(productDocument.id).update(
                mapOf("price" to product.price, "quantity" to dbProduct.quantity + product.quantity)
            )
        } catch (e: Exception) {
            Log.e(TAG, "addProducts: ${e.localizedMessage}")
            firestore.collection("products_collection").document().set(product)
        }
    }

    @ExperimentalCoroutinesApi
    override fun getAllProducts(): Flow<List<Product>> {
        return callbackFlow {
            val listener =
                firestore.collection("products")
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            throw error
                        } else {
                            value?.let {
                                val products = it.toObjects(Product::class.java)
                                trySend(products)
                            }
                        }
                    }
            awaitClose { listener.remove() }
        }

    }

    override suspend fun getLoggedInUser(): User {
        return firestore.collection("users_collection").whereEqualTo("uid", auth.uid!!).get()
            .await().toObjects(User::class.java)[0]
    }

    override suspend fun removeProduct(sellerId: String, id: String) {
        firestore.collection("products_collection").whereEqualTo("sellerId", sellerId)
            .whereEqualTo("productId", id).get().await().forEach {
                it.reference.delete()
            }
    }

    companion object {
        const val TAG = "REPOSITORY_IMPL"
    }

}