package com.dxn.data.models

data class CatalogueProduct(
    val productId:String="",
    val name: String = "",
    val photoUrl: String = "",
    val sellerId: String = "",
    val quantity: Int = 0,
    val price: Float = 0f,
)