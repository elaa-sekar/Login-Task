package com.technotackle.test.util

import android.content.Context
import android.widget.Toast

object ViewUtils {

    fun Context.showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}