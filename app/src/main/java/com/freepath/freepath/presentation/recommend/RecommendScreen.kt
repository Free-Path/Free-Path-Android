package com.freepath.freepath.presentation.recommend

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.freepath.freepath.ui.theme.FreePathTheme

@Composable
fun RecommendScreen(
    modifier: Modifier = Modifier,
) {
    FreePathTheme {
        RecommendNav(modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendTopAppBar(
    modifier: Modifier = Modifier,
    navContent: @Composable () -> Unit = {},
    title: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navContent,
    )
}