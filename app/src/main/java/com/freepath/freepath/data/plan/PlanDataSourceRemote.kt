package com.freepath.freepath.data.plan

import com.freepath.freepath.presentation.model.Plan
import com.freepath.freepath.presentation.model.Recommend
import java.time.LocalDate

interface PlanDataSourceRemote {
    suspend fun updatePlan(id: Int, plan: Plan): Result<Unit>

    suspend fun getPlan(id: Int): Result<PlanResponse>

    suspend fun getRecommendedPlan(recommend: Recommend): Result<Int>

    suspend fun addPlan(plan: Plan): Result<Int>

    suspend fun getPlanListByDate(date: LocalDate): List<List<PlanResponse>>
}