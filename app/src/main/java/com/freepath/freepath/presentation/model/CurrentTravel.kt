package com.freepath.freepath.presentation.model

data class CurrentTravel (
    val currentTravelImg: String,
    val currentTravelTitle: String,
    val currentTravelPeriod: String,
    val currentTravelRoute: List<String>
)