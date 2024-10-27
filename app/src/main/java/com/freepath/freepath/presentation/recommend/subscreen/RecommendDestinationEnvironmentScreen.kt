package com.freepath.freepath.presentation.recommend.subscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Slider
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.freepath.freepath.R
import com.freepath.freepath.presentation.common.FirstTimeLaunchEffect
import com.freepath.freepath.presentation.recommend.RecommendViewModel
import com.freepath.freepath.ui.theme.FreePathTheme

@Composable
fun RecommendDestinationEnvironmentScreen(
    viewModel: RecommendViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    onClickNext: () -> Unit = {},
) {
    val destination by remember { viewModel.destination }
    val environmentValue by remember { viewModel.environmentValue }
    RecommendDestinationEnvironmentScreen(
        destination = destination,
        selectDestination = viewModel::selectDestination,
        environmentValue.toFloat(),
        onClickNext,
        onClickBack,
        modifier,
    ) { changedValue: Float ->
        viewModel.changeEnvironmentValue((changedValue + 0.1f).toInt())
    }
}

@Composable
fun RecommendDestinationEnvironmentScreen(
    destination: String,
    selectDestination: (String) -> Unit,
    environmentValue: Float,
    onClickNext: () -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    onValueChange: (Float) -> Unit,
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
        Spacer(Modifier.height(8.dp))
        DropdownMenuUneditable(
            selected = destination,
            items = destinationItems.toList(),
            selectItem = selectDestination
        )

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