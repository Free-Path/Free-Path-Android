package com.freepath.freepath.presentation.recommend.subscreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
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
    val checkedList = remember { viewModel.targetCheckList }
    RecommendTargetScreen(
        checkedList,
        onClickNext,
        viewModel::changeTargetChecked,
        onClickBack,
        modifier
    )
}

@Composable
private fun RecommendTargetScreen(
    checkedList: List<Boolean>,
    onClickNext: () -> Unit,
    onClickCheck: (Int) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val targetTextArray = stringArrayResource(R.array.targets)
    RecommendFrame(onClickNext = onClickNext, onClickBack = onClickBack, modifier = modifier) {
        Text("여행의 목적이 무엇인가요?", fontSize = MaterialTheme.typography.titleLarge.fontSize)
        Spacer(Modifier.height(8.dp))
        CheckBoxGroup(checkedList, onClickCheck, targetTextArray)

    }
}