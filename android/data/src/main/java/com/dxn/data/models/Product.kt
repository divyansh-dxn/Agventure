package com.dxn.data.models

//val crops = mapOf(
//    1 to Product("BITTER GUARD",""),
//    2 to Product("POTATO"),
//    3 to Product("TOMATO"),
//    4 to Product("BRINJAL"),
//    5 to Product("BANANA"),
//    6 to Product("PUMPKIN"),
//    7 to Product("GARLIC"),
//    8 to Product("LEMON")
//)

data class Product(
    val id : String = "",
    val name: String = "",
    val photoUrl: String = ""
)

data class CatalogueProduct(
    val sellerId: String = "",
    val productId: String = "",
    val quantity: Int = 0,
    val price: Float = 0f,
)