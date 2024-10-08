package com.freepath.freepath.ui.plan

import androidx.lifecycle.ViewModel
import com.freepath.freepath.ui.model.Plan
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor() : ViewModel() {
    private val _plan = MutableStateFlow(
        Plan(LocalDate.now(), List(5) { List(3) { planDetailEx } }.toMutableList())
    )
    val plan = _plan.asStateFlow()
}