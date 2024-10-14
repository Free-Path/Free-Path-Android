package com.freepath.freepath.presentation.recommend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.freepath.freepath.R
import com.freepath.freepath.presentation.common.FilterChipGroup
import com.freepath.freepath.presentation.common.FilterChipState
import com.freepath.freepath.presentation.common.NumberPlusMinusButton

@Composable
fun RecommendTogetherScreen(
    modifier: Modifier = Modifier,
    viewModel: RecommendViewModel = hiltViewModel(),
    onClickNext: () -> Unit,
) {
    val ageStringArray = stringArrayResource(R.array.ages)
    val count by remember { viewModel.peopleCount }
    val chipStateList by remember {
        derivedStateOf {
            viewModel.ageStateList.zip(ageStringArray) { selected, text ->
                FilterChipState(selected, text)
            }
        }
    }
    println(chipStateList)

    RecommendTogetherScreen(
        count,
        chipStateList,
        viewModel::minusPeopleCount,
        viewModel::plusPeopleCount,
        viewModel::changeAgeStateChecked,
        modifier,
        onClickNext
    )
}

@Composable
fun RecommendTogetherScreen(
    peopleCount: Int,
    chipStateList: List<FilterChipState>,
    onClickMinusButton: () -> Unit,
    onClickPlusButton: () -> Unit,
    onClickChip: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onClickNext: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val buttonHeight = remember(configuration.screenHeightDp) {
        (configuration.screenHeightDp / 10).coerceIn(40..60).dp
    }
    val scrollState = rememberScrollState()
    Column(modifier) {
        Column(
            Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .verticalScroll(scrollState)
                .weight(1f)
        ) {
            Text("누구와 여행하시나요?", fontSize = 24.sp)
            Spacer(Modifier.height(8.dp))
            Text("연령")
            FilterChipGroup(
                chipStateList,
                buttonHeight = buttonHeight,
                onClickChip = onClickChip,
                columnSize = 3
            )
            Spacer(Modifier.height(16.dp))
            Text("총 인원 수")
            NumberPlusMinusButton(
                peopleCount,
                Modifier.align(Alignment.CenterHorizontally),
                onClickPlus = onClickPlusButton,
                onClickMinus = onClickMinusButton
            )
            Spacer(Modifier.height(28.dp))
        }
        Button(
            onClick = onClickNext,
            Modifier
                .weight(0.3f)
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally)
                .wrapContentHeight()
        ) {
            Text("다음", Modifier.padding(vertical = 8.dp))
        }
    }
}






