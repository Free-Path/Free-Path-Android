package com.freepath.freepath.presentation.model

import java.time.LocalDate

data class PlanDate(
    val date: LocalDate,
    val planDetails: List<PlanDetail>,
)