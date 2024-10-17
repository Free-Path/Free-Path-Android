package com.freepath.freepath.presentation.recommend

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

@Composable
fun RecommendStyleScreen(
    modifier: Modifier = Modifier,
    viewModel: RecommendViewModel = hiltViewModel(),
    onClickNext: () -> Unit = {},
) {
    val checkedList = remember { viewModel.styleCheckList }
    RecommendStyleScreen(
        checkedList,
        modifier,
        onClickNext,
        viewModel::changeStyleChecked,
    )
}

@Composable
private fun RecommendStyleScreen(
    checkedList: List<Boolean>,
    modifier: Modifier = Modifier,
    onClickNext: () -> Unit,
    onClickCheck: (Int) -> Unit,
) {
    val styles = stringArrayResource(R.array.styles)
    RecommendFrame(onClickNext, modifier) {
        Column {
            Text("이런 도움이 필요해요.", fontSize = MaterialTheme.typography.titleLarge.fontSize)
            Spacer(Modifier.height(8.dp))
            CheckBoxGroup(checkedList, onClickCheck, styles)
        }
    }
}