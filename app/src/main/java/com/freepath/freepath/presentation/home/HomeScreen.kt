package com.freepath.freepath.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.freepath.freepath.R
import com.freepath.freepath.presentation.model.RecommendedTouristSpot
import com.freepath.freepath.presentation.model.CurrentTravel
import com.freepath.freepath.ui.theme.FreePathTheme
import com.freepath.freepath.ui.theme.Pretendard14
import com.freepath.freepath.ui.theme.Pretendard16
import com.freepath.freepath.ui.theme.Pretendard18
import com.freepath.freepath.ui.theme.Pretendard24
import com.freepath.freepath.ui.theme.TitleGray

@Composable
fun HomeScreen(isInProgressTravel: Boolean) {

    Surface(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        color = Color.White
    ) {
        val isInProgressTravel = remember { mutableStateOf(isInProgressTravel) }
        val homeViewModel = viewModel<HomeViewModel>()

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
                style = Pretendard24,
                color = TitleGray,
                modifier = Modifier
                    .padding(10.dp, 5.dp)
            )

            upComingTravel(currentTravel = homeViewModel.mockCurrentTravel)

            Spacer(
                modifier = Modifier
                    .padding(10.dp)
            )

            Text(
                text = stringResource(R.string.home_recommended_tourist_spot),
                style = Pretendard24,
                color = TitleGray,
                modifier = Modifier
                    .padding(10.dp, 5.dp)
            )

            recommendedTouristSpot(recommendTouristSpot = homeViewModel.mockRecommendedTouristSpot)
        }
    }
}

@Composable
fun upComingTravel(currentTravel: CurrentTravel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = currentTravel.currentTravelImg,
                contentDescription = null,
                alignment = Alignment.Center,
                contentScale = ContentScale.FillWidth,
                alpha = 0.5f
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = currentTravel.currentTravelTitle,
                    style = Pretendard18
                )
                Text(
                    text = currentTravel.currentTravelPeriod,
                    style = Pretendard16
                )
                currentTravel.currentTravelRoute.forEach { route ->
                    Text(
                        text = route,
                        style = Pretendard14
                    )
                }
            }
        }
    }
}

@Composable
fun recommendedTouristSpot(recommendTouristSpot: List<RecommendedTouristSpot>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
        ) {
            customStaggeredVerticalGrid(
                numColumns = 2,
                modifier = Modifier.padding(5.dp)
            ) {
                recommendTouristSpot.forEach { recommendTouristSpot ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = recommendTouristSpot.imageUrl,
                                contentDescription = null,
                                alignment = Alignment.Center,
                            )

                            Text(
                                text = recommendTouristSpot.placeName,
                                style = Pretendard16,
                                modifier = Modifier.padding(vertical = 10.dp)
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