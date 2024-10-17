package com.freepath.freepath.presentation.model

data class UpcomingTravel (
    val upcomingTravelImg: String,
    val upcomingTravelTitle: String,
    val upcomingTravelPeriod: String,
    val upcomingTravelRoute: List<String>
)