package com.freepath.freepath.presentation.plan

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Place
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.freepath.freepath.R
import com.freepath.freepath.presentation.common.TextShort
import com.freepath.freepath.presentation.model.PlanDate
import com.freepath.freepath.presentation.model.PlanDetail
import com.freepath.freepath.presentation.model.planDetailEx
import com.freepath.freepath.ui.theme.FreePathTheme
import com.freepath.freepath.ui.theme.Green60
import com.freepath.freepath.presentation.util.first
import com.freepath.freepath.presentation.util.getName
import com.freepath.freepath.presentation.util.last
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun PlanColumn(
    planDates: List<PlanDate>,
    dates: List<LocalDate>,
    onClickPlanDetail: (PlanDetail) -> Unit,
    onClickChangePlan: () -> Unit,
    modifier: Modifier = Modifier,
    isStartReached: (Boolean) -> Unit,
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val sumCounts by remember(dates, planDates) {
        derivedStateOf {
            buildList<Int>(dates.size) {
                var sum = 0
                planDates.forEach {
                    add(sum)
                    sum += it.planDetails.size + 1
                }
            }
        }
    }

    LaunchedEffect(listState.canScrollBackward) {
        isStartReached(listState.canScrollBackward.not())
    }

    val firstItemIndex by remember {
        derivedStateOf { listState.firstVisibleItemIndex }
    }
    LaunchedEffect(firstItemIndex) {
        val index = sumCounts.binarySearch(firstItemIndex + 1)
        selectedTabIndex = if (index < 0) {
            -index - 1
        } else {
            index
        } - 1
    }

    Column(modifier = modifier) {
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DateTabRow(selectedTabIndex, dates, Modifier.weight(1f, true)) { rowIndex ->
                selectedTabIndex = rowIndex
                coroutineScope.launch {
                    listState.animateScrollToItem(sumCounts[rowIndex.coerceIn(0 until sumCounts.last())])
                }
            }
            Button(
                onClick = onClickChangePlan,
                modifier = Modifier
                    .weight(0.3f, false)
                    .padding(horizontal = 4.dp)
            ) {
                Text("편집")
            }
        }
        HorizontalDivider()
        PlanItems(
            onClickItem = onClickPlanDetail,
            planDates = planDates,
            listState = listState
        )
    }
}

@Composable
fun DateTabRow(
    selectedTabIndex: Int,
    dates: List<LocalDate>,
    modifier: Modifier = Modifier,
    onClickTab: (Int) -> Unit,
) {
    ScrollableTabRow(
        edgePadding = 0.dp,
        modifier = modifier.absolutePadding(left = 0.dp),
        selectedTabIndex = selectedTabIndex,
    ) {
        dates.forEachIndexed { rowIndex, date ->
            DateTab(rowIndex, selectedTabIndex, date, onClickTab = onClickTab)
        }
    }
}

@Composable
private fun DateTab(
    index: Int,
    selectedTabIndex: Int,
    date: LocalDate,
    modifier: Modifier = Modifier,
    onClickTab: (Int) -> Unit = {},
) {
    Tab(
        selected = index == selectedTabIndex,
        onClick = { onClickTab(index) },
        modifier = modifier.wrapContentWidth(),
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("${index + 1}일차")
                Text(
                    stringResource(
                        R.string.month_day_str,
                        date.monthValue,
                        date.dayOfMonth,
                        date.dayOfWeek.getName()
                    )
                )
            }
        },
    )
}

@Composable
fun PlanItems(
    planDates: List<PlanDate>,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onClickItem: (PlanDetail) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        state = listState,
    ) {
        planDates.forEachIndexed { index, plans ->
            item {
                Column(Modifier.padding(vertical = 4.dp)) {
                    if (index != 0) {
                        HorizontalDivider()
                    }
                    Text("${index + 1}일차", Modifier.padding(vertical = 8.dp), fontSize = 20.sp)
                }
            }
            items(plans.planDetails, key = PlanDetail::id) { plan ->
                PlanItemDetail(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClickItem(plan) },
                    planDetail = plan.copy(likes = plan.likes + index)
                )
            }
        }
    }
}

@Composable
fun PlanItemDetail(
    modifier: Modifier = Modifier,
    planDetail: PlanDetail,
) {
    val colorScheme = MaterialTheme.colorScheme
    val placeHolder = remember {
        BrushPainter(
            Brush.linearGradient(
                listOf(Color(color = 0xFFC6C6C6), Color(color = colorScheme.primary.value.toInt()))
            )
        )
    }
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = Green60,
                thickness = 2.dp
            )
            Icon(
                Icons.TwoTone.Place, null, tint = Green60,
                modifier = Modifier.background(colorScheme.surface)
            )
        }
        AsyncImage(
            model = planDetail.thumbnail,
            modifier = Modifier
                .width(120.dp)
                .aspectRatio(1.5f),
            contentDescription = planDetail.title,
            alignment = Alignment.Center,
            clipToBounds = false,
            placeholder = placeHolder,
            contentScale = ContentScale.Crop
        )
        Column(Modifier.padding(start = 8.dp)) {
            TextShort(text = planDetail.title)
            TextShort(text = planDetail.category, fontSize = 10.sp)
            TextShort(text = "${Char(0x2764)}  ${planDetail.likes}", fontSize = 10.sp)
            planDetail.operating?.run {
                TextShort(
                    text = "${start.dayOfWeek.getName()} " +
                            stringResource(R.string.hour_minute, first.hour, first.minute) +
                            " ~ " +
                            stringResource(R.string.hour_minute, last.hour, last.minute),
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTabRow() {
    DateTab(0, 0, LocalDate.now())
}

@Preview(
    name = "Night",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(name = "Day", showBackground = true)
@Composable
private fun PreviewPlanDetail() {
    FreePathTheme {
        Surface {
            PlanItemDetail(
                modifier = Modifier.wrapContentSize(),
                planDetail = planDetailEx
            )
        }
    }
}