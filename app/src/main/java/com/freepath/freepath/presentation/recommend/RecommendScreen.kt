package com.freepath.freepath.presentation.recommend

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RecommendScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            RecommendTopAppBar {
                IconButton({}) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                }
            }
        }
    ) { innerPadding ->
        RecommendNav(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendTopAppBar(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = {},
        modifier = modifier,
        navigationIcon = icon,
    )
}