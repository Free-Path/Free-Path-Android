package com.freepath.freepath.ui.model

import java.time.LocalDateTime

data class PlanDetail(
    val id:Int,
    val thumbnail: String,
    val title: String,
    val likes: Int,
    val category: String,
    val operating: ClosedRange<LocalDateTime>? = null,
    val price: Int? = null,
)

val planDetailEx = PlanDetail(
    id = 1,
    thumbnail = "https://www.kh.or.kr/jnrepo/namo/img/images/000045/20230405103334542_MPZHA77B.jpg",
    title = "경복궁",
    likes = 1768,
    category = "유적/문화재",
    operating = LocalDateTime.of(2024, 10, 3, 10, 0)..LocalDateTime.of(2024, 10, 3, 20, 0),
    price = null,
)