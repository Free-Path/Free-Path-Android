package com.freepath.freepath.ui.model

import java.time.LocalDateTime

data class PlanDetail(
    val thumbnail: String,
    val title: String,
    val likes: Int,
    val category: String,
    val operating: ClosedRange<LocalDateTime>? = null,
    val price: Int? = null,
)