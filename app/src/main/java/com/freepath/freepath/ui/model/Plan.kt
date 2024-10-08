package com.freepath.freepath.ui.model

import java.time.LocalDate

data class Plan(val planDates: List<PlanDate>) {
    val dates = planDates.map(PlanDate::date)
}

val planEx = Plan(List(5) { day ->
    PlanDate(
        LocalDate.now().plusDays(1L + day),
        List(3) { index -> planDetailEx.copy(id = day * 100 + index) }
    )
})