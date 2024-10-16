package com.freepath.freepath.presentation.planchange

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.freepath.freepath.R
import com.freepath.freepath.presentation.model.Plan
import com.freepath.freepath.presentation.model.PlanDetail
import com.freepath.freepath.presentation.model.planDetailEx
import com.freepath.freepath.presentation.plan.PlanItemDetail
import com.freepath.freepath.presentation.util.getName
import java.time.LocalDate

@Composable
fun PlanChangeScreen(
    modifier: Modifier = Modifier,
    viewModel: PlanChangeViewModel = hiltViewModel(),
    onClickCancel: () -> Unit,
    onClickComplete: () -> Unit,
) {
    val plan by viewModel.plan.collectAsState()
    PlanChangeScreen(plan, modifier, onClickCancel, onClickComplete,
        onClickAdd = { index ->
            viewModel.addPlanDetail(index, plan.planDates.first().planDetails.first())
        }, 
        onClickChangePosition = {
            // TODO: 여행지 순서 변경
        }, 
        onClickChangeLocation = {
            // TODO: 다른 여행지로 변경
        })
}

@Composable
private fun PlanChangeScreen(
    plan: Plan,
    modifier: Modifier = Modifier,
    onClickCancel: () -> Unit,
    onClickComplete: () -> Unit,
    onClickAdd: (Int) -> Unit,
    onClickChangePosition: () -> Unit,
    onClickChangeLocation: () -> Unit,
) {
    println(plan.planDates.first().planDetails.size)
    val planDetailsList by remember(plan) {
        derivedStateOf {
            plan.planDates.map { it.planDetails }
        }
    }
    PlanChange(
        plan.dates,
        planDetailsList,
        modifier,
        onClickAdd,
        onClickCancel,
        onClickComplete
    )
}

@Composable
private fun PlanChange(
    dates: List<LocalDate>,
    planDetailsList: List<List<PlanDetail>>,
    modifier: Modifier = Modifier,
    onClickAdd: (Int) -> Unit,
    onClickCancel: () -> Unit,
    onClickComplete: () -> Unit,
) {
    Column(modifier.fillMaxWidth()) {
        TopMenu(onClickCancel, onClickComplete)
        LazyColumn {
            itemsIndexed(planDetailsList) { index, planDetails ->
                if (index != 0) {
                    HorizontalDivider(Modifier.padding(vertical = 4.dp))
                }
                DayTitle(index + 1, dates[index]) {
                    onClickAdd(index)
                }
                if (planDetails.isNotEmpty()) {
                    planDetails.forEach { plan ->
                        PlanDetailChangeable(plan) {
                            // TODO: 아이템 체크 이벤트 발생
                        }
                    }
                } else {
                    Text("장소를 추가해주세요", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
            }
        }
    }
}


@Composable
private fun DayTitle(
    day: Int,
    date: LocalDate,
    modifier: Modifier = Modifier,
    onClickAdd: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            "${day}일차 ${
                stringResource(
                    R.string.month_day_str,
                    date.monthValue,
                    date.dayOfMonth,
                    date.dayOfWeek.getName()
                )
            }"
        )
        Button(
            onClick = onClickAdd,
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            Text("+ 장소 추가")
        }
    }
}

@Composable
private fun TopMenu(
    onClickCancel: () -> Unit,
    onClickComplete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        HorizontalDivider(thickness = 1.dp)
        Row(
            modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("취소",
                Modifier
                    .clickable { onClickCancel() }
                    .padding(4.dp),
                color = MaterialTheme.colorScheme.error
            )
            Text("완료",
                Modifier
                    .clickable { onClickComplete() }
                    .padding(4.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }
        HorizontalDivider(thickness = 1.dp)
    }
}

@Composable
private fun PlanDetailChangeable(
    planDetail: PlanDetail,
    modifier: Modifier = Modifier,
    onClickCheck: (Boolean) -> Unit,
) {
    var checked by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier
            .fillMaxWidth()
            .clickable {
                checked = checked.not()
                onClickCheck(checked)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            if (checked) {
                Icons.Filled.CheckCircle
            } else {
                Icons.Outlined.CheckCircle
            },
            if (checked) {
                "체크 됨"
            } else {
                "체크 해제됨"
            },
            tint = if (checked) MaterialTheme.colorScheme.primary else Color.Gray
        )
        PlanItemDetail(planDetail = planDetail)
    }
}

@Preview
@Composable
private fun PreviewTopMenu() {
    TopMenu({}, {})
}

@Preview(showBackground = true)
@Composable
private fun PreviewDay() {
    Column {
        DayTitle(1, LocalDate.now()) {}
        List(2) { planDetailEx }.forEach {
            PlanDetailChangeable(it) {}
        }
    }
}