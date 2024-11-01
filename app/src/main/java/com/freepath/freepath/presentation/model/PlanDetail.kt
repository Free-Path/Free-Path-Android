package com.freepath.freepath.presentation.model

import com.naver.maps.geometry.LatLng
import java.time.LocalTime

data class PlanDetail(
    val id: Int,
    val thumbnail: String,
    val title: String,
    val likes: Int,
    val category: String,
    val latLng: LatLng = LatLng.INVALID,
    val operating: ClosedRange<LocalTime>? = null,
    val price: Int? = null,
)

val planDetailEx = PlanDetail(
    id = 1,
    thumbnail = "https://www.kh.or.kr/jnrepo/namo/img/images/000045/20230405103334542_MPZHA77B.jpg",
    title = "경복궁",
    likes = 1768,
    category = "유적/문화재",
    operating = LocalTime.of(10, 0)..LocalTime.of(20, 0),
    price = null,
    latLng = LatLng(37.57207, 126.97917)
)