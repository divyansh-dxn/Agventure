package com.dxn.data.models

data class User(
    val name: String = "",
    val phoneNumber: String = "",
    val role : Int = 0
)

// 0 - consumer
// 1 - seller
