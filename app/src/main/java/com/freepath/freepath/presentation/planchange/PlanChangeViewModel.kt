package com.freepath.freepath.presentation.planchange

import androidx.lifecycle.ViewModel
import com.freepath.freepath.presentation.model.PlanDetail
import com.freepath.freepath.presentation.model.planEx
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PlanChangeViewModel @Inject constructor() : ViewModel() {
    private val _plan = MutableStateFlow(planEx)
    val plan = _plan.asStateFlow()

    fun addPlanDetail(index: Int, planDetail: PlanDetail) {
        _plan.value = _plan.value.copy(
            planDates = _plan.value.planDates.mapIndexed { i, planDate ->
                if (i == index) {
                    planDate.copy(planDetails = planDate.planDetails + planDetail)
                } else {
                    planDate
                }
            }
        )
    }
}