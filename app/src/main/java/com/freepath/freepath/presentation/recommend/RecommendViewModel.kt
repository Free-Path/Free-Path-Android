package com.freepath.freepath.presentation.recommend

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freepath.freepath.data.plan.PlanDataSourceRemote
import com.freepath.freepath.presentation.model.Disability
import com.freepath.freepath.presentation.model.Recommend
import com.kizitonwose.calendar.core.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val planDataSourceRemote: PlanDataSourceRemote,
) : ViewModel() {
    var firstDay = mutableStateOf<CalendarDay?>(null)
        private set
    var secondDay = mutableStateOf<CalendarDay?>(null)
        private set

    private val _disabilityCheckList = mutableStateListOf(*Array(10) { false })
    val disabilityCheckList: List<Boolean> = _disabilityCheckList

    var peopleCount = mutableIntStateOf(1)
        private set

    private val _ageStateList = mutableStateListOf(*Array(9) { false })
    val ageStateList: List<Boolean> = _ageStateList

    var environmentValue = mutableIntStateOf(4)
        private set

    var destination = mutableStateOf("")
        private set

    private val _themeCheckList = mutableStateListOf(*Array(10) { false })
    val themeCheckList: List<Boolean> = _themeCheckList

    private val _targetCheckList = mutableStateListOf(*Array(10) { false })
    val targetCheckList: List<Boolean> = _targetCheckList

    private val _styleCheckList = mutableStateListOf(*Array(10) { false })
    val styleCheckList: List<Boolean> = _styleCheckList

    var createdId: MutableState<Int?> = mutableStateOf(null)
        private set

    fun updateDays(firstDay: CalendarDay?, secondDay: CalendarDay?) {
        this.firstDay.value = firstDay
        this.secondDay.value = secondDay
    }

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
    }

    fun changeDisabilityChecked(index: Int) {
        _disabilityCheckList[index] = disabilityCheckList[index].not()
    }

    fun changeEnvironmentValue(value: Int) {
        environmentValue.intValue = value
    }

    fun selectDestination(value: String) {
        destination.value = value
    }

    fun changeThemeChecked(index: Int) {
        _themeCheckList[index] = themeCheckList[index].not()
    }

    fun changeTargetChecked(index: Int) {
        _targetCheckList[index] = targetCheckList[index].not()
    }

    fun changeStyleChecked(index: Int) {
        _styleCheckList[index] = styleCheckList[index].not()
    }

    fun getRecommendPlan() {
        viewModelScope.launch {
            try {
                val recommend = Recommend(
                    firstDay = firstDay.value!!.date,
                    lastDay = secondDay.value!!.date,
                    peopleCount = peopleCount.intValue,
                    destination = "서울",
                    disabilities = ageStateList
                        .mapIndexed { index, bool -> if (bool) Disability.entries[index] else null }
                        .filterNotNull(),
                    ages = ageStateList
                        .mapIndexed { index, bool -> if (bool) index + 1 else null }
                        .filterNotNull(),
                    themes = themeCheckList
                        .mapIndexed { index, bool -> if (bool) index + 1 else null }
                        .filterNotNull(),
                    targets = targetCheckList
                        .mapIndexed { index, bool -> if (bool) index + 1 else null }
                        .filterNotNull(),
                    visits = styleCheckList
                        .mapIndexed { index, bool -> if (bool) index + 1 else null }
                        .filterNotNull(),
                    environments = environmentValue.intValue
                )
                Log.d(TAG, "recommend $recommend")
                planDataSourceRemote.getRecommendedPlan(recommend)
                    .onSuccess {
                        createdId.value = it
                    }.onFailure {
                        createdId.value = -1
                    }
            } catch (e: NullPointerException) {
                e.printStackTrace()
                createdId.value = -1
            }
        }
    }

    companion object {
        const val TAG = "RecommendViewModel"
    }
}
