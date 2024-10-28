package com.freepath.freepath.presentation.travel.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.freepath.freepath.R
import com.freepath.freepath.presentation.common.RecommendTag
import com.freepath.freepath.presentation.model.TouristSpotDetail
import com.freepath.freepath.ui.theme.FreePathTheme
import com.freepath.freepath.ui.theme.Pretendard12
import com.freepath.freepath.ui.theme.Pretendard14
import com.freepath.freepath.ui.theme.Pretendard16
import com.freepath.freepath.ui.theme.Pretendard20
import com.freepath.freepath.ui.theme.Pretendard24
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.Align
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerDefaults
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState

class TouristSpotDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreePathTheme {
                val touristSpotDetailViewModel = viewModel<TouristSpotDetailViewModel>()
                TouristSpotDetailScreen(
                    touristSpotDetail = touristSpotDetailViewModel.mockTouristSpotDetail,
                    touristRecommend = touristSpotDetailViewModel.mockTouristSpotDetailRecommend
                )
            }
        }
    }
}

@Composable
fun TouristSpotDetailScreen(touristSpotDetail: TouristSpotDetail, touristRecommend: List<String>) {
    Surface(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TouristSpotDetailImgList(touristSpotDetailImgList = touristSpotDetail.placeImg)

            Text(
                text = touristSpotDetail.placeName,
                style = Pretendard24,
                modifier = Modifier
                    .padding(top = 12.dp)
            )

            Text(
                text = touristSpotDetail.placeAddress,
                style = Pretendard14,
                modifier = Modifier
                    .padding(top = 4.dp)
            )

            Text(
                text = touristSpotDetail.placeDescription,
                style = Pretendard12,
                modifier = Modifier
                    .padding(top = 4.dp)
            )

            Spacer(Modifier.height(6.dp))

            TouristSpotRecommendList(
                touristSpotDetailRecommendList = touristRecommend
            )

            Text(
                text = stringResource(R.string.spot_detail_business_hour),
                modifier = Modifier
                    .padding(top = 12.dp),
                style = Pretendard20
            )

            Text(
                text = touristSpotDetail.placeBusinessHour,
                modifier = Modifier
                    .padding(top = 4.dp),
                style = Pretendard16
            )

            Text(
                text = stringResource(R.string.spot_detail_barrier_free),
                modifier = Modifier
                    .padding(top = 12.dp),
                style = Pretendard20
            )

            Spacer(Modifier.height(2.dp))

            touristSpotDetail.placeBarrierFree.forEach { barrierFreeInfo ->
                Text(
                    text = barrierFreeInfo,
                    modifier = Modifier
                        .padding(top = 2.dp),
                    style = Pretendard16
                )
            }

            Spacer(Modifier.height(12.dp))

            TouristSpotDetailMap(
                placeName = touristSpotDetail.placeName,
                lat = touristSpotDetail.placeLatitude,
                lng = touristSpotDetail.placeLongitude
            )
        }
    }
}

@Composable
fun TouristSpotDetailImgList(
    modifier: Modifier = Modifier,
    touristSpotDetailImgList: List<String>
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(touristSpotDetailImgList) { imgList ->
            AsyncImage(
                model = imgList,
                contentDescription = null,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(350.dp, 200.dp)
            )
        }
    }
}

@Composable
fun TouristSpotRecommendList(
    modifier: Modifier = Modifier,
    touristSpotDetailRecommendList: List<String>
) {
    Row(modifier) {
        touristSpotDetailRecommendList.forEach { recommend ->
            RecommendTag(
                text = recommend,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun TouristSpotDetailMap(placeName: String, lat: Double, lng: Double) {
    val position = LatLng(lat, lng)
    val cameraPositionState = rememberCameraPositionState{
        this.position = CameraPosition(position, 15.0)
    }
    NaverMap(
        modifier = Modifier.fillMaxWidth().requiredHeight(400.dp),
        onMapClick = { _, _ -> { } },
        content = {
            Marker(
                state = rememberMarkerState(
                    position = position
                ),
                captionText = placeName,
                icon = MarkerDefaults.Icon,
                captionAligns = arrayOf(Align.Top, Align.Right)
            )
        },
        uiSettings = MapUiSettings(isZoomControlEnabled = false),
        cameraPositionState = cameraPositionState
    )
}

@Preview(showBackground = true)
@Composable
fun TouristSpotDetailPreview() {
    FreePathTheme {
        val touristSpotDetailViewModel = viewModel<TouristSpotDetailViewModel>()
        TouristSpotDetailScreen(
            touristSpotDetail = touristSpotDetailViewModel.mockTouristSpotDetail,
            touristRecommend = touristSpotDetailViewModel.mockTouristSpotDetailRecommend
        )
    }
}
