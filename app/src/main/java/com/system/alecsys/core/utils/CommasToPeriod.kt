package com.system.alecsys.core.utils

fun String.commaToPeriod(): String {
    return if (this.contains(",")) {
        this.replace(",", ".")
    } else return this
}