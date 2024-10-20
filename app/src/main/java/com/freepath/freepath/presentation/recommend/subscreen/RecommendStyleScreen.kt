package com.freepath.freepath.presentation.recommend.subscreen

import androidx.compose.foundation.layout.Column
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
fun RecommendStyleScreen(
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecommendViewModel = hiltViewModel(),
    onClickNext: () -> Unit = {},
) {
    val checkedList = remember { viewModel.styleCheckList }
    RecommendStyleScreen(
        checkedList,
        onClickBack,
        modifier,
        onClickNext,
        viewModel::changeStyleChecked,
    )
}

@Composable
private fun RecommendStyleScreen(
    checkedList: List<Boolean>,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    onClickNext: () -> Unit,
    onClickCheck: (Int) -> Unit,
) {
    val styles = stringArrayResource(R.array.styles)
    RecommendFrame(onClickNext, onClickBack, modifier) {
        Column {
            Text(
                "방문하고 싶은\n여행지 스타일을 알려주세요!",
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
            Spacer(Modifier.height(8.dp))
            CheckBoxGroup(checkedList, onClickCheck, styles)
        }
    }
}