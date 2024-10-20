package com.freepath.freepath.presentation.recommend.subscreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.freepath.freepath.R
import com.freepath.freepath.presentation.common.FilterChipGroup
import com.freepath.freepath.presentation.common.FilterChipState
import com.freepath.freepath.presentation.common.NumberPlusMinusButton
import com.freepath.freepath.presentation.recommend.RecommendViewModel

@Composable
fun RecommendTogetherScreen(
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecommendViewModel = hiltViewModel(),
    onClickNext: () -> Unit = {},
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

    RecommendTogetherScreen(
        count,
        chipStateList,
        viewModel::minusPeopleCount,
        viewModel::plusPeopleCount,
        viewModel::changeAgeStateChecked,
        onClickBack,
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
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    onClickNext: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val buttonHeight = remember(configuration.screenHeightDp) {
        (configuration.screenHeightDp / 10).coerceIn(40..60).dp
    }
    RecommendFrame(onClickNext = onClickNext, onClickBack = onClickBack, modifier = modifier) {
        Text("누구와 여행하시나요?", fontSize = MaterialTheme.typography.titleLarge.fontSize)
        Spacer(Modifier.height(8.dp))
        Text("같이 여행하는 인원의 연령대를 모두 골라주세요")
        FilterChipGroup(
            chipStateList,
            buttonHeight = buttonHeight,
            onClickChip = onClickChip,
            columnSize = 3
        )
        Spacer(Modifier.height(16.dp))
        Text("같이 여행하는 인원은 총 몇 명인가요?", fontSize = MaterialTheme.typography.titleLarge.fontSize)
        NumberPlusMinusButton(
            peopleCount,
            Modifier.align(Alignment.CenterHorizontally),
            onClickPlus = onClickPlusButton,
            onClickMinus = onClickMinusButton
        )
    }
}






