package com.dedechandran.core.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

fun String.formatDate(): String {
    val format = LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    return format.format(DateTimeFormatter.ofPattern("d MMM yyyy"))
}

fun String.getYear(): String {
    val format = LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    return format.year.toString()
}