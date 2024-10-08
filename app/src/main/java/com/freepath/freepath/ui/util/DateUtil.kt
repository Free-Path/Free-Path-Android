package com.freepath.freepath.ui.util

import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

fun DayOfWeek.getName(): String = getDisplayName(TextStyle.SHORT, Locale.KOREAN)