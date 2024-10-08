package com.freepath.freepath.ui.model

import java.time.LocalDate

data class Plan(
    val startDate: LocalDate,
    val planDetailsList: MutableList<List<PlanDetail>>,
) {
    val endDate
        get() = startDate.plusDays(planDetailsList.sumOf(List<PlanDetail>::size).toLong())
}
