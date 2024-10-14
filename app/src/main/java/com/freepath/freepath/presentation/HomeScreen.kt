package com.freepath.freepath.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.freepath.freepath.R
import com.freepath.freepath.ui.theme.FreePathTheme
import com.freepath.freepath.ui.theme.TitleGray

@Composable
fun HomeScreen(isInProgressTravel: Boolean) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        val isInProgressTravel = remember { mutableStateOf(isInProgressTravel) }

        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (isInProgressTravel.value) {
                    stringResource(R.string.home_current_travel)
                } else {
                    stringResource(R.string.home_upcoming_travel)
                },
                color = TitleGray,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(10.dp, 5.dp)
            )

            upComingTravel()
            Spacer(
                modifier = Modifier
                    .padding(10.dp)
            )
            Text(
                text = stringResource(R.string.home_recommended_tourist_spot),
                color = TitleGray,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(10.dp, 5.dp)
            )

            recommendedTouristSpot()
        }
    }
}

@Composable
fun upComingTravel() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Image(
            painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "images",
            alignment = Alignment.Center
        )


    }
}

@Composable
fun recommendedTouristSpot() {
    // TODO image 서버에서 coil로 받아서 출력
    val images = listOf(
        R.drawable.ic_launcher_background,
        com.naver.maps.map.R.drawable.navermap_default_cluster_icon_low_density,
        R.drawable.ic_bnv_welfare_32,
        R.drawable.ic_launcher_foreground,
        com.naver.maps.map.R.drawable.navermap_default_marker_icon_lightblue,
        R.drawable.ic_bnv_home_32,
        com.naver.maps.map.R.drawable.navermap_default_ground_overlay_image
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(5.dp)
        ) {
            customStaggeredVerticalGrid(
                numColumns = 2,
                modifier = Modifier.padding(5.dp)
            ) {
                images.forEach { img ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.CenterHorizontally),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painterResource(id = img),
                                contentDescription = "images",
                                alignment = Alignment.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun customStaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    numColumns: Int = 2,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurable, constraints ->
        val columnWidth = (constraints.maxWidth / numColumns)
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val columnHeights = IntArray(numColumns) { 0 }
        val placeables = measurable.map { measurable ->
            val column = testColumn(columnHeights)
            val placeable = measurable.measure(itemConstraints)

            columnHeights[column] += placeable.height
            placeable
        }

        val height =
            columnHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
                ?: constraints.minHeight

        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val columnYPointers = IntArray(numColumns) { 0 }

            placeables.forEach { placeables ->
                val column = testColumn(columnYPointers)

                placeables.place(
                    x = columnWidth * column,
                    y = columnYPointers[column]
                )

                columnYPointers[column] += placeables.height
            }
        }
    }
}

private fun testColumn(columnHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var columnIndex = 0
    columnHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            columnIndex = index
        }
    }

    return columnIndex
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    FreePathTheme {
        HomeScreen(isInProgressTravel = true)
    }
}