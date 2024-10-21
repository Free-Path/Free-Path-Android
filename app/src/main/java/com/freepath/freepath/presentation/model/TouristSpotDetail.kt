package com.freepath.freepath.presentation.model

data class TouristSpotDetail(
    val placeId: Long,
    val placeImg: List<String>,
    val placeName: String,
    val placeDescription: String,
    val placeBusinessHour: String,
    val placeAddress: String,
    val placeLatitude: Double,
    val placeLongitude: Double,
    val placeBarrierFree: List<String>,
    val placeLikes: Int
)