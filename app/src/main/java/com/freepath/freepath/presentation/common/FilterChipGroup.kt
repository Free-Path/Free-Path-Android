package com.freepath.freepath.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FilterChipGroup(
    filterChipState: List<FilterChipState>,
    modifier: Modifier = Modifier,
    columnSize: Int = 2,
    buttonHeight: Dp = Dp.Unspecified,
    onClickChip: (Int) -> Unit,
) {
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        for (columnIndex in filterChipState.indices step columnSize) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                for (rowIndex in 0 until columnSize) {
                    val index = columnIndex + rowIndex
                    if (index !in filterChipState.indices) {
                        Spacer(
                            Modifier
                                .padding(horizontal = 8.dp)
                                .weight(1f)
                        )
                    } else {
                        FilterChip(
                            selected = filterChipState[index].selected,
                            onClick = { onClickChip(index) },
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .height(buttonHeight)
                                .weight(1f),
                            colors = FilterChipDefaults.filterChipColors().copy(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                selectedContainerColor = MaterialTheme.colorScheme.inversePrimary
                            ),
                            border = null,
                            label = {
                                Text(
                                    filterChipState[index].text,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            },
                            trailingIcon = filterChipState[index].icon
                        )
                    }
                }
            }
        }
    }
}

@Stable
data class FilterChipState(
    val selected: Boolean,
    val text: String,
    val icon: @Composable (() -> Unit)? = null,
)

@Preview
@Composable
private fun PreviewFilterChipGroup() {
    val list = List(8) { FilterChipState(true, "버튼$it") }
    Column {
        FilterChipGroup(list, onClickChip = {}, columnSize = 3)
    }
}