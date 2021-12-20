package com.dxn.data.models


val products = mapOf(
    1 to Product("1","Banana", "https://firebasestorage.googleapis.com/v0/b/dxn-agventure.appspot.com/o/products%2Fbanana.jpg?alt=media&token=24eec8ed-3b94-4ef7-a060-4b6cace72ca2"),
    2 to Product("2","Brinjal", "https://firebasestorage.googleapis.com/v0/b/dxn-agventure.appspot.com/o/products%2Fbrinjal.JPG?alt=media&token=e1a36927-e4bd-4a4b-8291-fc70be571951"),
    3 to Product("3","Pumplin", "https://firebasestorage.googleapis.com/v0/b/dxn-agventure.appspot.com/o/products%2Fbrinjal.JPG?alt=media&token=e1a36927-e4bd-4a4b-8291-fc70be571951"),
    3 to Product("4","Garlic", "https://firebasestorage.googleapis.com/v0/b/dxn-agventure.appspot.com/o/products%2Fpumpkin.jpeg?alt=media&token=45d4808a-373e-4652-8aa4-b1cf1fd4d10f"),
)

data class Product(
    val id : String = "",
    val name: String = "",
    val photoUrl: String = ""
)