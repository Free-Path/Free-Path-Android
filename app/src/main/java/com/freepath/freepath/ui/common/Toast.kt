package com.freepath.freepath.ui.common

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(@StringRes resId: Int) {
    showToast(getString(resId))
}

fun Context.showToast(@StringRes resId: Int, vararg formatArgs: Any) {
    showToast(getString(resId, *formatArgs))
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}