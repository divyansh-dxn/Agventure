package com.dxn.data.models

val crops = mapOf(
    1 to "BITTER GUARD",
    2 to "POTATO",
    3 to "TOMATO",
    4 to "BRINJAL"
)

data class Product(
    val name: String = "",
    val photoUrl: String = ""
)

data class CatalogueProduct(
    val sellerId: String,
    val productId: String,
    val quantity: Int,
    val price: Float,
)