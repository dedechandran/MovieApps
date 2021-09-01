package com.dedechandran.core.utils

fun Int.convertDuration(): String {
    val ONE_HOUR = 60
    var hour = 0
    var minute = 0
    var newDuration = this
    if (newDuration < ONE_HOUR) {
        minute = newDuration
        return "${minute}m"
    } else {
        while (newDuration >= ONE_HOUR) {
            val remainder = newDuration % ONE_HOUR
            if (remainder == 0) {
                hour += 1
                newDuration -= ONE_HOUR
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