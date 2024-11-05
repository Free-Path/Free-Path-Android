package com.freepath.freepath.data.plan

import com.freepath.freepath.presentation.model.Plan
import com.freepath.freepath.presentation.model.PlanDetail
import java.time.LocalDate

object PlanMockServer {
    private var planResponseList = mutableListOf<PlanResponse>(

    )

    private var planDetailList = mutableListOf<PlanDetail>(
    )

    fun getPlan(id: Int): PlanResponse {
        return planResponseList.first { it.id == id }
    }

    fun updatePlan(id: Int, plan: Plan) {
        for (i in 0 until planResponseList.size) {
            if (planResponseList[i].id == id) {
                planResponseList[i] = planResponseList[i].copy(plan = plan)
            }
        }
        sortPlanList()
    }

    fun addPlan(plan: Plan): Int {
        val id = (planResponseList.maxOfOrNull { it.id } ?: 0) + 1
        planResponseList += PlanResponse(plan, id)
        sortPlanList()
        return id
    }

    // 과거, 현재, 미래 순으로 추가
    fun getPlans(date: LocalDate): List<List<PlanResponse>> {
        val result = listOf(mutableListOf<PlanResponse>())

        val past = planResponseList.filter { date > it.plan.dates.last() }.take(5)
        result[0].addAll(past)

        val now = planResponseList.filter { date in it.plan.dates }
        result[1].addAll(now)

        val future = planResponseList.filter { date < it.plan.dates.first() }.takeLast(5)
        result[2].addAll(future)

        return result
    }

    fun getPlanDetail(id: Int): PlanDetail {
        return planDetailList.first { it.id == id }
    }

    private fun sortPlanList() {
        planResponseList.sortBy { it.plan.dates.first() }
    }
}