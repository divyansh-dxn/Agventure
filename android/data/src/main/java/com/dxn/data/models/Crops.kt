package com.dxn.data.models

val crops = mapOf(
    1 to "BITTER GUARD",
    2 to "POTATO",
    3 to "TOMATO",
    4 to "BRINJAL",
    5 to "BANANA",
    6 to "PUMPKIN",
    7 to "GARLIC",
    8 to "LEMON"
)

data class Product(
    val name: String = "",
    val photoUrl: String = ""
)

data class CatalogueProduct(
    val sellerId: String = "",
    val productId: String = "",
    val quantity: Int = 0,
    val price: Float = 0f,
)