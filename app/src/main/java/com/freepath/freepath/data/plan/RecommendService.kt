package com.freepath.freepath.data.plan

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RecommendService {
    @POST("recommend")
    suspend fun fetchRecommendPlan(@Body recommend: RecommendRequest): Response<String>
}

