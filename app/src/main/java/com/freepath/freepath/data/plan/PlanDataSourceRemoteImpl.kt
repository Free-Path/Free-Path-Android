package com.freepath.freepath.data.plan

import com.freepath.freepath.presentation.model.Plan
import com.freepath.freepath.presentation.model.Recommend
import kotlinx.coroutines.delay
import java.time.LocalDate
import javax.inject.Inject

class PlanDataSourceRemoteImpl @Inject constructor(
    private val planService: PlanService,
) : PlanDataSourceRemote {
    override suspend fun updatePlan(id: Int, plan: Plan): Result<Unit> {
        PlanMockServer.updatePlan(id, plan)
        return Result.success(Unit)
    }

    override suspend fun getPlan(id: Int): Result<PlanResponse> {
        val planResponse = PlanMockServer.getPlan(id)
        return Result.success(planResponse)
    }

    override suspend fun getRecommendedPlan(recommend: Recommend): Result<Int> {
        delay(2000)
        return Result.success(10)
    }

    override suspend fun addPlan(plan: Plan): Result<Int> {
        val planId = PlanMockServer.addPlan(plan)
        return Result.success(planId)
    }

    override suspend fun getPlanListByDate(date:LocalDate):List<List<PlanResponse>> {
        return PlanMockServer.getPlans(date)
    }
}