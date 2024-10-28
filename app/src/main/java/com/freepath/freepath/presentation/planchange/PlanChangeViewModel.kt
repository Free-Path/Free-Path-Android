package com.freepath.freepath.presentation.planchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freepath.freepath.data.plan.PlanDataSourceRemote
import com.freepath.freepath.presentation.model.Plan
import com.freepath.freepath.presentation.model.PlanDetail
import com.freepath.freepath.presentation.model.planEx
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanChangeViewModel @Inject constructor(
    private val planDataSourceRemote: PlanDataSourceRemote,
) : ViewModel() {
    private val planIdFlow = MutableSharedFlow<Int>(1)

    private val changedPlan = MutableStateFlow<Plan?>(null)

    val plan: StateFlow<Plan?> = planIdFlow.combine(changedPlan) { planId, changed ->
        if (changed == null) {
            planDataSourceRemote.getPlan(planId)
        } else {
            Result.success(changed)
        }
    }.map { result ->
        result.map {
            planEx
        }.getOrElse {
            null
        }
    }.onEach {
        println(it)
        if (changedPlan.value == null) {
            changedPlan.value = it
        }
    }.catch {
        it.printStackTrace()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun updatePlanId(planId: Int) {
        viewModelScope.launch {
            planIdFlow.emit(planId)
        }
    }

    fun addPlanDetail(index: Int, planDetail: PlanDetail) {
        changedPlan.value?.let { value ->
            changedPlan.value = value.copy(
                planDates = value.planDates.mapIndexed { i, planDate ->
                    if (i == index) {
                        planDate.copy(planDetails = planDate.planDetails + planDetail)
                    } else {
                        planDate
                    }
                }
            )
        }
    }

    suspend fun updatePlan(): Boolean {
        return viewModelScope.async(Dispatchers.IO) {
            val id = planIdFlow.replayCache.first()
            changedPlan.value?.let {
                return@async planDataSourceRemote.updatePlan(id, it).isSuccess
            } ?: false
        }.await()
    }
}