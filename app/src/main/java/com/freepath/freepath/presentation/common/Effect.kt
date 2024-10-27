package com.freepath.freepath.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope

@Composable
fun FirstTimeLaunchEffect(
    key: Any = Unit,
    onLaunch: CoroutineScope.() -> Unit,
) {
    val launched = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key) {
        if (launched.value.not()) {
            onLaunch()
            launched.value = true
        }
    }
}