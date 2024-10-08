package com.freepath.freepath.ui.util

import java.time.LocalDateTime

val ClosedRange<LocalDateTime>.first get() = start
val ClosedRange<LocalDateTime>.last get() = endInclusive
