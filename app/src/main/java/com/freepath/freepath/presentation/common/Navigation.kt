package com.freepath.freepath.presentation.common

import android.app.Activity
import android.content.Context
import android.content.Intent

fun <T : Activity> navigateToActivity(
    context: Context,
    activity: Class<T>,
    setExtra: Intent.() -> Unit = {},
) {
    val intent = Intent(context, activity)
    intent.setExtra()
    context.startActivity(intent)
}