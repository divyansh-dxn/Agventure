package com.dxn.seller.domain

import com.dxn.data.models.CatalogueProduct
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getProducts(sellerId:String) : Flow<List<CatalogueProduct>>
    suspend fun addProducts( product: CatalogueProduct )
}