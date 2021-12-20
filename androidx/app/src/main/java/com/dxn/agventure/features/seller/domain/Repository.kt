package com.dxn.agventure.features.seller.domain

import com.dxn.data.models.CatalogueProduct
import com.dxn.data.models.Product
import com.dxn.data.models.User
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getProducts(sellerId:String) : Flow<List<CatalogueProduct>>
    suspend fun addProducts( sellerId: String,product: CatalogueProduct)
    fun getAllProducts() : Flow<List<Product>>
    suspend fun getLoggedInUser() : User
    suspend fun removeProduct(sellerId: String, id: String)
}