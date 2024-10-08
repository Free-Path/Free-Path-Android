package com.freepath.freepath.ui.common

import androidx.compose.ui.Modifier

fun Modifier.thenIf(condition: Boolean, modify: Modifier.() -> Modifier) =
    if (condition) {
        then(modify(Modifier))
    } else {
        this
    }