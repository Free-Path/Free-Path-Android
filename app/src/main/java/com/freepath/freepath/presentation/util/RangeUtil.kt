package com.freepath.freepath.presentation.util

val <T : Comparable<T>> ClosedRange<T>.first get() = start
val <T : Comparable<T>> ClosedRange<T>.last get() = endInclusive
