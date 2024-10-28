package com.freepath.freepath.presentation.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freepath.freepath.data.plan.PlanDataSourceRemote
import com.freepath.freepath.presentation.model.Plan
import com.freepath.freepath.presentation.model.planEx
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val planDataSourceRemote: PlanDataSourceRemote,
) : ViewModel() {
    private val planIdFlow = MutableSharedFlow<Int>(1)

    val plan: StateFlow<Plan?> = planIdFlow
        .map { planDataSourceRemote.getPlan(it) }
        .map { result ->
            result.map {
                planEx
            }.getOrElse {
                null
            }
        }.catch {
            it.printStackTrace()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun updatePlanId(planId: Int) {
        viewModelScope.launch {
            planIdFlow.emit(planId)
        }
    }
}
