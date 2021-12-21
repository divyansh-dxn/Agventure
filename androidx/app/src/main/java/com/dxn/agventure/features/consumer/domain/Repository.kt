package com.dxn.agventure.features.consumer.domain

import com.dxn.data.models.CatalogueProduct
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getProducts() : Flow<List<CatalogueProduct>>
}