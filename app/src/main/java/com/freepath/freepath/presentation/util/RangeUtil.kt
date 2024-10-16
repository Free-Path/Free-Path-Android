package com.freepath.freepath.presentation.util

import java.time.LocalDateTime

val ClosedRange<LocalDateTime>.first get() = start
val ClosedRange<LocalDateTime>.last get() = endInclusive
