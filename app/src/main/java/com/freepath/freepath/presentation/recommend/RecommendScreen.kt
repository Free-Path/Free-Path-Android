package com.freepath.freepath.presentation.recommend

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.freepath.freepath.ui.theme.FreePathTheme
import kotlin.math.roundToInt

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
        modifier = modifier.fillMaxWidth().layout { measurable, constraints ->
            val paddingCompensation = 16.dp.toPx().roundToInt()
            val adjustedConstraints = constraints.copy(
                maxWidth = constraints.maxWidth + paddingCompensation
            )
            val placeable = measurable.measure(adjustedConstraints)
            layout(placeable.width, placeable.height) {
                placeable.place(-paddingCompensation / 2, 0)
            }
        },
        navigationIcon = navContent,
    )
}