package com.freepath.freepath.presentation.recommend.subscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.freepath.freepath.R
import com.freepath.freepath.presentation.common.CheckBoxGroup
import com.freepath.freepath.presentation.recommend.RecommendViewModel

@Composable
fun RecommendTargetScreen(
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecommendViewModel = hiltViewModel(),
    onClickNext: () -> Unit = {},
) {
    val environmentValue by remember { viewModel.environmentValue }
    val checkedList = remember { viewModel.targetCheckList }
    RecommendTargetScreen(
        checkedList,
        environmentValue.toFloat(),
        onClickNext,
        viewModel::changeTargetChecked,
        onClickBack,
        modifier
    ) { changedValue: Float ->
        viewModel.changeEnvironmentValue((changedValue + 0.1f).toInt())
    }
}

@Composable
private fun RecommendTargetScreen(
    checkedList: List<Boolean>,
    environmentValue: Float,
    onClickNext: () -> Unit,
    onClickCheck: (Int) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    onValueChange: (Float) -> Unit,
) {
    val targetTextArray = stringArrayResource(R.array.targets)
    RecommendFrame(onClickNext = onClickNext, onClickBack = onClickBack, modifier = modifier) {
        Text("여행의 목적이 무엇인가요?", fontSize = MaterialTheme.typography.titleLarge.fontSize)
        Spacer(Modifier.height(8.dp))
        CheckBoxGroup(checkedList, onClickCheck, targetTextArray)

        Spacer(Modifier.height(32.dp))

        Text("선호하는 환경이 무엇인가요?", fontSize = MaterialTheme.typography.titleLarge.fontSize)
        Spacer(Modifier.height(8.dp))
        EnvironmentSliderBar(
            environmentValue,
            Modifier.padding(horizontal = 4.dp),
            onValueChange
        )
    }
}

@Composable
private fun EnvironmentSliderBar(
    value: Float,
    modifier: Modifier = Modifier,
    onValueChange: (Float) -> Unit,
) {
    Column(modifier) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("자연 선호")
            Text("중립")
            Text("도시 선호")
        }
        Slider(
            value = value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            valueRange = 1f..7f,
            steps = 5,
            onValueChange = onValueChange,
        )
    }
}

@Preview
@Composable
private fun PreviewSliderBar() {
    EnvironmentSliderBar(5f) {}
}