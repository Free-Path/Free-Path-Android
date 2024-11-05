package com.freepath.freepath.data.plan

import com.freepath.freepath.presentation.model.Plan
import com.freepath.freepath.presentation.model.PlanDetail
import com.naver.maps.geometry.LatLng
import java.time.LocalDate
import java.time.LocalTime

object PlanMockServer {
    private var planResponseList = mutableListOf<PlanResponse>()

    var planDetailList = mutableListOf<PlanDetail>(
        PlanDetail(
            1,
            "https://i.namu.wiki/i/DK-BcaE6wDCM-N9UJbeQTn0SD9eWgsX9YKWK827rqjbrzDz0-CxW-JFOCiAsUL3CBZ4zE0UDR-p4sLaYPiUjww.webp",
            "남산 서울타워",
            6262,
            "관광 명소",
            LatLng(37.5511694, 126.9882266)
        ),
        PlanDetail(
            2,
            "https://lh5.googleusercontent.com/p/AF1QipOkHFi0nvsSgNdR7GA0qAXB70WWAi2ZKhdtLEUU=w408-h408-k-no",
            "CGV 피카디리1958",
            1032,
            "영화관",
            LatLng(37.5710359, 126.9912291)
        ),
        PlanDetail(
            3,
            "https://lh5.googleusercontent.com/p/AF1QipP9G6CZUUD7U3z-Km2c2TDsfWk5c3HX0z8d6K3_=w426-h240-k-no",
            "익선동 한옥마을",
            2821,
            "명소",
            LatLng(37.5737132, 126.9901271)
        ),
        PlanDetail(
            4,
            "https://lh5.googleusercontent.com/p/AF1QipPDHwqLeDx0fzjZHRTi2erQeKUgW9w_OW6rX80-=w408-h271-k-no",
            "탑골공원",
            1678,
            "공원",
            LatLng(37.5711455, 126.9883295)
        ),
        PlanDetail(
            5,
            "https://lh5.googleusercontent.com/p/AF1QipO0QmDh5JU6y1PY31U2X0dY4mpgvZOm1r6zA4Ae=w408-h306-k-no",
            "청계천",
            4812,
            "공원",
            LatLng(37.5691015, 126.9786692)
        ),
        PlanDetail(
            id = 6,
            thumbnail = "https://www.kh.or.kr/jnrepo/namo/img/images/000045/20230405103334542_MPZHA77B.jpg",
            title = "경복궁",
            likes = 1768,
            category = "유적/문화재",
            operating = LocalTime.of(9, 0)..LocalTime.of(17, 30),
            price = null,
            latLng = LatLng(37.57207, 126.97917)
        )
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