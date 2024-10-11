package com.freepath.freepath.presentation.plan

import androidx.lifecycle.ViewModel
import com.freepath.freepath.presentation.model.planEx
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor() : ViewModel() {
    private val _plan = MutableStateFlow(planEx)
    val plan = _plan.asStateFlow()
}