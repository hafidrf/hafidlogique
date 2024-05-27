package com.hafidrf.app.core.common.ext.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Melakukan aksi pada field [EditText] ketika terdapat perubahan value dan mengembalikan value
 * dengan tipe data [String]
 * @param EditText field yang akan di ambil valuenya
 * @param onChanged aksi yang dijalankan ketika value dari [EditText] berubah dan diteruskan ke parent class
 */
fun EditText.onChangeText(onChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onChanged.invoke(p0.toString())
        }

        override fun afterTextChanged(editable: Editable?) {

        }
    })
}