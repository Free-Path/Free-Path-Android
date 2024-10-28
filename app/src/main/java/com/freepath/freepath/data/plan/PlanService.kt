package com.freepath.freepath.data.plan

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanService {
    @GET
    suspend fun fetchPlan(@Query("id") id: Int): Response<PlanResponse>
}