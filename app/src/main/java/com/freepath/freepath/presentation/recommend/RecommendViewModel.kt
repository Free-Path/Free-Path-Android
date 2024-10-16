package com.freepath.freepath.presentation.recommend

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor() : ViewModel() {
    private val _disabilityCheckList = mutableStateListOf(*Array(10) { false })
    val disabilityCheckList: List<Boolean> = _disabilityCheckList

    var peopleCount = mutableIntStateOf(1)
        private set

    private val _ageStateList = mutableStateListOf(*Array(9) { false })
    val ageStateList: List<Boolean> = _ageStateList

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
//        _ageStateList[index] = ageStateList[index].copy(selected = ageStateList[index].selected.not())
        _ageStateList[index] = ageStateList[index].not()
        println(_ageStateList)
    }

    fun changeDisabilityChecked(index: Int) {
        _disabilityCheckList[index] = disabilityCheckList[index].not()
    }
}
