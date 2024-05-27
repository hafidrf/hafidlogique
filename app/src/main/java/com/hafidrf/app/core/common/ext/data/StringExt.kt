package com.hafidrf.app.core.common.ext.data

fun String?.dashIfEmpty() : String{
    if(this == null)
        return "-"

    return this
}