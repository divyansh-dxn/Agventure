package com.dxn.agventure.utils

fun formatPhoneNumber(number: String): String {
    var formattedNumber = ""
    if (number.length != 10) {
        // throw a invalid format exception
    }
    var j = 0
    for (i in 0..11) {
        if (i == 3 || i == 7) {
            formattedNumber = formattedNumber.plus("-")
        } else {
            println(j)
            formattedNumber = formattedNumber.plus(number[j])
            j++;
        }
    }
    return formattedNumber
}