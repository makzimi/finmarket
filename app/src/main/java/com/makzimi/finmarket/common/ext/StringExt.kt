package com.makzimi.finmarket.common.ext

import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.fromDateToDate(formatFrom: String, formatTo: String): String {
    val input = SimpleDateFormat(formatFrom)
    val output = SimpleDateFormat(formatTo)

    var d: Date? = null
    try {
        d = input.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    var formatted = ""
    try {
        formatted = output.format(d)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return formatted
}