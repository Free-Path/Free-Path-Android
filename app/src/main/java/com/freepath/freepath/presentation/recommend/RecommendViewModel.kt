package com.freepath.freepath.presentation.recommend

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor() : ViewModel() {
    private val _disabilityCheckList = mutableStateListOf(*Array(10) { false })
    val disabilityCheckList: List<Boolean> = _disabilityCheckList

    var peopleCount = mutableIntStateOf(1)
        private set

    private val _ageStateList = mutableStateListOf(*Array(9) { false })
    val ageStateList: List<Boolean> = _ageStateList

    var environmentValue = mutableIntStateOf(4)
        private set

    private val _targetCheckList = mutableStateListOf(*Array(10) { false })
    val targetCheckList: List<Boolean> = _targetCheckList

    private val _styleCheckList = mutableStateListOf(*Array(10) { false })
    val styleCheckList: List<Boolean> = _styleCheckList

    var isCreationComplete = mutableStateOf(false)
        private set

    fun plusPeopleCount() {
        if (peopleCount.intValue < 20) {
            peopleCount.intValue++
        }
    }

    fun minusPeopleCount() {
        if (peopleCount.intValue > 1) {
            peopleCount.intValue--
        }
    }

    fun changeAgeStateChecked(index: Int) {
        _ageStateList[index] = ageStateList[index].not()
        println(_ageStateList)
    }

    fun changeDisabilityChecked(index: Int) {
        _disabilityCheckList[index] = disabilityCheckList[index].not()
    }

    fun changeEnvironmentValue(value: Int) {
        environmentValue.intValue = value
    }

    fun changeTargetChecked(index: Int) {
        _targetCheckList[index] = targetCheckList[index].not()
    }

    fun changeStyleChecked(index: Int) {
        _styleCheckList[index] = styleCheckList[index].not()
    }

    fun getRecommendPlan() {
        println("getRecommendPlan")
        viewModelScope.launch {
            delay(3_000L)
            isCreationComplete.value = true
        }
    }
}
