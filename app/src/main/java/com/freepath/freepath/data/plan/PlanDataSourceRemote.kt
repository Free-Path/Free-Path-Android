package com.freepath.freepath.data.plan

import com.freepath.freepath.presentation.model.Plan

interface PlanDataSourceRemote {
    suspend fun updatePlan(id: Int, plan: Plan): Result<Unit>

    suspend fun getPlan(id: Int): Result<PlanResponse>

    suspend fun getRecommendedPlan(): Result<Int>
}