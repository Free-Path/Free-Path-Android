package com.freepath.freepath.data.plan

import com.freepath.freepath.presentation.model.Plan
import com.freepath.freepath.presentation.model.Recommend
import com.freepath.freepath.presentation.model.planEx
import kotlinx.coroutines.delay
import javax.inject.Inject

class PlanDataSourceRemoteImpl @Inject constructor(
    private val planService: PlanService,
) : PlanDataSourceRemote {
    override suspend fun updatePlan(id: Int, plan: Plan): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun getPlan(id: Int): Result<PlanResponse> {
//        val response = planService.fetchPlan(id)
//        return try {
//            if (response.isSuccessful) {
//                Result.success(requireNotNull(response.body()) { "Response body require not null" })
//            } else {
//                val error = response.errorBody()?.string()
//                    ?: return Result.failure(IOException(response.code().toString()))
//                Result.failure(IOException(error))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
        return Result.success(PlanResponse(planEx))
    }

    override suspend fun getRecommendedPlan(recommend: Recommend): Result<Int> {
        delay(2000)
        return Result.success(10)
    }
}