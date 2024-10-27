package com.freepath.freepath.presentation.recommend.subscreen

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.freepath.freepath.R
import com.freepath.freepath.presentation.common.FirstTimeLaunchEffect
import com.freepath.freepath.presentation.recommend.RecommendViewModel
import com.freepath.freepath.ui.theme.FreePathTheme

@Composable
fun RecommendDestinationScreen(
    viewModel: RecommendViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    onClickNext: () -> Unit = {},
) {
    val destination by remember { viewModel.destination }
    RecommendDestinationScreen(
        destination = destination,
        selectDestination = viewModel::selectDestination,
        onClickNext,
        onClickBack,
        modifier,
    )
}

@Composable
fun RecommendDestinationScreen(
    destination: String,
    selectDestination: (String) -> Unit,
    onClickNext: () -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val destinationItems = stringArrayResource(R.array.destinations)
    FirstTimeLaunchEffect {
        if (destination.isBlank()) {
            selectDestination(destinationItems.first())
        }
    }
    RecommendFrame(
        onClickNext = onClickNext,
        onClickBack = onClickBack,
        modifier = modifier,
        isEnabledNextButton = destination.isNotBlank(),
    ) {
        Text("여행하고 싶은 지역을 선택해주세요.")
        DropdownMenuUneditable(
            selected = destination,
            items = destinationItems.toList(),
            selectItem = selectDestination
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuUneditable(
    selected: String,
    items: List<String>,
    modifier: Modifier = Modifier,
    selectItem: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier,
    ) {
        TextField(
            value = selected,
            onValueChange = { selectItem(it) },
            readOnly = true,
            singleLine = true,
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        selectItem(item)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
private fun PreviewDropdownMenuUneditable() {
    val list = List(5) { "item $it" }
    FreePathTheme {
        DropdownMenuUneditable(list.first(), list) {}
    }
}