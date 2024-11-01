package com.freepath.freepath.presentation.travel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.freepath.freepath.R
import com.freepath.freepath.presentation.common.navigateToPlanActivity
import com.freepath.freepath.presentation.model.CurrentTravel
import com.freepath.freepath.presentation.model.PreviousTravel
import com.freepath.freepath.presentation.model.UpcomingTravel
import com.freepath.freepath.ui.theme.Pretendard14
import com.freepath.freepath.ui.theme.Pretendard16
import com.freepath.freepath.ui.theme.Pretendard18
import com.freepath.freepath.ui.theme.Pretendard24
import com.freepath.freepath.ui.theme.TitleGray

@Composable
fun TravelScreen(
    onClickFloating: () -> Unit,
    travelViewModel: TravelViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Surface(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if (travelViewModel.isCurrentTravel.value) {
                    Text(
                        text = stringResource(R.string.travel_current_travel),
                        style = Pretendard24,
                        color = TitleGray,
                        modifier = Modifier
                            .padding(10.dp, 5.dp)
                    )

                    CurrentTravelCard(travelViewModel.mockCurrentTravel)
                }

                Spacer(
                    modifier = Modifier
                        .padding(10.dp)
                )

                Text(
                    text = stringResource(R.string.travel_upcoming_travel),
                    style = Pretendard24,
                    color = TitleGray,
                    modifier = Modifier
                        .padding(10.dp, 5.dp)
                )

                travelViewModel.mockUpcomingTravel.forEach { upcomingTravel ->
                    UpcomingTravelList(upcomingTravel = upcomingTravel)
                }

                Spacer(
                    modifier = Modifier
                        .padding(10.dp)
                )

                Text(
                    text = stringResource(R.string.travel_previous_travel),
                    style = Pretendard24,
                    color = TitleGray,
                    modifier = Modifier
                        .padding(10.dp, 5.dp)
                )

                travelViewModel.mockPreviousTravel.forEach { previousTravel ->
                    PreviousTravelList(previousTravel = previousTravel)
                }
            }
        }

        FloatingActionButton(
            onClick = onClickFloating,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }

}

@Composable
fun CurrentTravelCard(currentTravel: CurrentTravel) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                navigateToPlanActivity(context, 10)
            },
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
fun UpcomingTravelList(upcomingTravel: UpcomingTravel) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        Column {
            Text(
                text = upcomingTravel.upcomingTravelDday,
                style = Pretendard16,
                color = Color.Red
            )
            AsyncImage(
                model = upcomingTravel.upcomingTravelImg,
                contentDescription = null,
                alignment = Alignment.Center,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(52.dp)
                    .padding(top = 5.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 22.dp, start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = upcomingTravel.upcomingTravelName,
                style = Pretendard16
            )
            Text(
                text = upcomingTravel.upcomingTravelPeriod,
                style = Pretendard14
            )
        }
    }
}

@Composable
fun PreviousTravelList(previousTravel: PreviousTravel) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        AsyncImage(
            model = previousTravel.previousTravelImg,
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .size(52.dp)
                .padding(top = 5.dp)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = previousTravel.previousTravelName,
                style = Pretendard16
            )
            Text(
                text = previousTravel.previousTravelPeriod,
                style = Pretendard14
            )
        }
    }
}