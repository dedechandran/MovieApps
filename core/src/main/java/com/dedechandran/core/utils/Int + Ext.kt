package com.dedechandran.core.utils

fun Int.convertDuration(): String {
    val oneHour = 60
    var hour = 0
    var minute = 0
    var newDuration = this
    if (newDuration < oneHour) {
        minute = newDuration
        return "${minute}m"
    } else {
        while (newDuration >= oneHour) {
            val remainder = newDuration % oneHour
            if (remainder == 0) {
                hour += 1
                newDuration -= oneHour
            } else {
                minute = remainder
                newDuration -= remainder
            }
        }
        return if (minute == 0) {
            "${hour}h"
        } else {
            "${hour}h ${minute}m"
        }
    }
}