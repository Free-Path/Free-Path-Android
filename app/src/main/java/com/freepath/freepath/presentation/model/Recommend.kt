package com.freepath.freepath.presentation.model

import java.time.LocalDate

data class Recommend(
    val firstDay: LocalDate,
    val lastDay: LocalDate,
    val peopleCount: Int,
    val destination: String,
    val disabilities: List<Disability>,
    val ages: List<Int>,
    val themes: List<Int>,
    val targets: List<Int>,
    val visits: List<Int>,
    val environments: Int,
)
