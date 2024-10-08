package com.freepath.freepath.ui.model

import java.time.LocalDate

data class PlanDate(
    val date: LocalDate,
    val planDetails: List<PlanDetail>,
)