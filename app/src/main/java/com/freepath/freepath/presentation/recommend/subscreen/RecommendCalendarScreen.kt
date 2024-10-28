package com.freepath.freepath.presentation.recommend.subscreen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.freepath.freepath.R
import com.freepath.freepath.presentation.common.thenIf
import com.freepath.freepath.presentation.recommend.RecommendViewModel
import com.freepath.freepath.presentation.recommend.subscreen.CalendarDayRange.Companion.rangeTo
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale

@Composable
fun RecommendCalendarScreen(
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecommendViewModel = hiltViewModel(),
    onClickNext: () -> Unit = {},
) {
    val firstDay by remember { viewModel.firstDay }
    val lastDay by remember { viewModel.secondDay }
    RecommendCalendarScreen(
        firstDay = firstDay,
        lastDay = lastDay,
        onClickNext = onClickNext,
        onClickBack = onClickBack,
        changeDays = viewModel::updateDays,
        modifier = modifier
    )
}

@Composable
private fun RecommendCalendarScreen(
    firstDay: CalendarDay?,
    lastDay: CalendarDay?,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
    changeDays: (day1: CalendarDay?, day2: CalendarDay?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dayRange = firstDay..lastDay
    RecommendFrame(
        onClickBack = onClickBack,
        onClickNext = onClickNext,
        isEnabledNextButton = (dayRange?.diffDay() ?: 1000) in 1..2,
    ) {
        Text(
            "❗ 여행 일정은 2~3일만 선택 가능합니다.",
            Modifier.fillMaxWidth(),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        CalendarLibrary(firstDay, lastDay, changeDays, modifier)
    }
}

@Composable
fun CalendarLibrary(
    firstDay: CalendarDay?,
    lastDay: CalendarDay?,
    changeDays: (day1: CalendarDay?, day2: CalendarDay?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val currentMonth = remember { YearMonth.of(2024, 10) }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val daysOfWeek = remember { daysOfWeek() }
    val clickedRange = remember(firstDay, lastDay) { firstDay..lastDay }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    Column {
        HorizontalCalendar(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp)),
            state = state,
            dayContent = { day ->
                Day(day, clickedRange = clickedRange) {
                    when {
                        firstDay == null && lastDay == it -> changeDays(null, null)
                        firstDay == null -> changeDays(it, lastDay)
                        firstDay == it -> changeDays(null, lastDay)
                        lastDay == null -> changeDays(firstDay, it)
                        lastDay == it -> changeDays(firstDay, null)
                        else -> changeDays(it, null)
                    }
                }
            },
            monthHeader = { month ->
                val week = remember { month.weekDays.first().map { it.date.dayOfWeek } }
                DaysOfWeekTitle(
                    month,
                    daysOfWeek = week,
                    onClickBackButton = {
                        coroutineScope.launch {
                            state.animateScrollToMonth(
                                month.yearMonth.minusMonths(1L)
                            )
                        }
                    },
                    onClickNextButton = {
                        coroutineScope.launch {
                            state.animateScrollToMonth(
                                month.yearMonth.plusMonths(1L)
                            )
                        }
                    }

                )
            },
        )
        HorizontalDivider(
            modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        )
        SelectedDays(clickedRange)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCalendarLibrary() {
    CalendarLibrary(null, null, { _, _ -> }, Modifier.padding(16.dp))
}

@Composable
private fun Day(
    day: CalendarDay,
    modifier: Modifier = Modifier,
    clickedRange: CalendarDayRange? = null,
    onClick: (CalendarDay) -> Unit = {},
) {
    val colorScheme = MaterialTheme.colorScheme
    val selectedBackground = remember {
        Modifier
            .padding(vertical = 4.dp)
            .background(colorScheme.surfaceContainerHigh)
    }
    val isOutDate = remember(day, clickedRange) {
        day.position != DayPosition.MonthDate || day.date < LocalDate.now()
    }
    Box(
        modifier = modifier
            .clickable(
                enabled = day.date >= LocalDate.now(),
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) { onClick(day) }
            .aspectRatio(1f)
            .thenIf(clickedRange != null && day in clickedRange) {
                if (day.date == clickedRange!!.first.date || day.date == clickedRange.last.date) {
                    Modifier
                        .padding(vertical = 4.dp)
                        .background(
                            colorScheme.surfaceContainerHigh,
                            if (day.date == clickedRange.first.date) {
                                RoundedCornerShape(topStartPercent = 30, bottomStartPercent = 30)
                            } else {
                                RoundedCornerShape(topEndPercent = 30, bottomEndPercent = 30)
                            }
                        )
                } else {
                    selectedBackground
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = when {
                isOutDate -> Color.Gray
                day.date.dayOfWeek == DayOfWeek.SUNDAY -> Color.Red
                else -> MaterialTheme.colorScheme.onBackground
            }
        )
    }
}

@Preview
@Composable
private fun PreviewDay() {
    Box(modifier = Modifier.size(20.dp)) {
        Day(CalendarDay(date = LocalDate.of(2024, 10, 5), position = DayPosition.InDate))
    }
}

@Composable
private fun DaysOfWeekTitle(
    month: CalendarMonth,
    daysOfWeek: List<DayOfWeek>,
    onClickBackButton: () -> Unit,
    onClickNextButton: () -> Unit,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onClickBackButton
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Arrow Back",
                    modifier = Modifier
                        .wrapContentSize()
                        .aspectRatio(1f)
                )
            }
            Text(
                text = stringResource(
                    R.string.year_month,
                    month.yearMonth.year,
                    month.yearMonth.monthValue
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .align(alignment = Alignment.CenterVertically)
            )
            IconButton(
                onClick = onClickNextButton,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Arrow Forward",
                    modifier = Modifier
                        .wrapContentSize()
                        .aspectRatio(1f)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDaysOfWeekTitle() {
    DaysOfWeekTitle(
        month = CalendarMonth(YearMonth.of(2024, 10), emptyList()),
        daysOfWeek = daysOfWeek(), {}, {}
    )
}

@Composable
fun SelectedDays(
    selectedRange: CalendarDayRange?,
    modifier: Modifier = Modifier,
) {
    if (selectedRange == null || selectedRange.diffDay() == 1L) return
    val first = selectedRange.first.date
    val last = selectedRange.last.date
    val diffDay = selectedRange.diffDay()
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        SelectedDay(first, R.string.travel_start_day)
        SelectedDay(last, R.string.travel_end_day)
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = "${diffDay}박 ${diffDay + 1}일",
        )
    }
}

@Composable
fun SelectedDay(
    day: LocalDate,
    @StringRes titleId: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(stringResource(titleId))
        Text(
            text = stringResource(
                R.string.month_day_str,
                day.monthValue,
                day.dayOfMonth,
                day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSelectedDates() {
    SelectedDays(
        CalendarDay(LocalDate.now(), DayPosition.MonthDate)
                ..CalendarDay(LocalDate.now().plusDays(3L), DayPosition.MonthDate)
    )
}

private operator fun CalendarDay.compareTo(other: CalendarDay): Int {
    return date.compareTo(other.date)
}

data class CalendarDayRange(val first: CalendarDay, val last: CalendarDay) {
    init {
        require(first <= last) { "The start date must be before the end date." }
    }

    fun diffDay() = ChronoUnit.DAYS.between(first.date, last.date)

    operator fun contains(other: CalendarDay) = other >= first && other <= last

    companion object {
        operator fun CalendarDay?.rangeTo(other: CalendarDay?): CalendarDayRange? = when {
            this == null && other == null -> null
            this == null && other != null -> CalendarDayRange(other, other)
            this != null && other == null -> CalendarDayRange(this, this)
            this!! <= other!! -> CalendarDayRange(this, other)
            else -> CalendarDayRange(other, this)
        }
    }
}