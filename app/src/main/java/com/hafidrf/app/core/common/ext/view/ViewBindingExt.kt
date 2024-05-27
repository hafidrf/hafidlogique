package com.hafidrf.app.core.common.ext.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

// The magic reflection can reused everywhere.
inline fun <reified V : ViewBinding> ViewGroup.toBinding(): V {
    return V::class.java.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    ).invoke(null, LayoutInflater.from(context), this, false) as V
}