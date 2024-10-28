package com.freepath.freepath.presentation.travel.detail

import androidx.lifecycle.ViewModel
import com.freepath.freepath.presentation.model.TouristSpotDetail

class TouristSpotDetailViewModel : ViewModel() {
    val mockTouristSpotDetail = TouristSpotDetail(
        placeId = 1,
        placeImg = listOf(
            "https://cdn.news.eugenes.co.kr/news/photo/201608/2245_4430_324.jpg",
            "https://www.sangjomagazine.com/imgdata/sangjomagazine_com/202009/2020092936542609.jpg",
            "https://cdn.imweb.me/thumbnail/20220812/023a33fea8094.jpg"
        ),
        placeName = "경복궁",
        placeDescription = "조선시대의 궁궐 중 하나이자 조선의 정궁(법궁)이다. 태조가 조선을 건국하고 한양 천도를 단행하면서 조선 시대에 가장 먼저 지은 궁궐로, 1592년 임진왜란 때 소실되어 275년간 방치되다가 19세기에 흥선대원군 주도로 중건되었다",
        placeBusinessHour = "09:00 ~ 17:30",
        placeAddress = "서울특별시 종로구 사직로 161(세종로)",
        placeLatitude = 37.5808473,
        placeLongitude = 126.9768515,
        placeBarrierFree = listOf(
            "휠체어 충전소 : 북문에서 50m 거리",
            "엘리베이터 : 동2문에서 우측으로 도보 1분 거리",
            "경복궁역 3호선 대합실에서 지상층 엘리베이터"
        ),
        placeLikes = 4
    )

    val mockTouristSpotDetailRecommend = listOf(
        "🚌대중교통 가능",
        "😍핫플레이스",
        "♿배리어프리"
    )
}