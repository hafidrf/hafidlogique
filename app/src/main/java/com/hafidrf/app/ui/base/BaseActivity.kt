package com.hafidrf.app.ui.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity : AppCompatActivity() {

    open var page = 0

    override fun setContentView(view: View?) {
        super.setContentView(view)
    }
}