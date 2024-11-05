package com.freepath.freepath.presentation.travel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.freepath.freepath.presentation.model.CurrentTravel
import com.freepath.freepath.presentation.model.PreviousTravel
import com.freepath.freepath.presentation.model.UpcomingTravel

class TravelViewModel : ViewModel() {
    val isCurrentTravel = mutableStateOf(true)

    val mockCurrentTravel = CurrentTravel(
        currentTravelImg = "https://san.chosun.com/news/photo/201911/13272_55951_153.jpg",
        currentTravelTitle = "제주도",
        currentTravelPeriod = "2024.11.04~2024.11.07 (3박 4일)",
        currentTravelRoute = listOf(
            "· 1일차: 섭지코지, 성산일출봉",
            "· 2일차: 만장굴, 성읍민속마을",
            "· 3일차: 우도, 추자도, 국립제주박물관",
            "· 4일차: 제주 올레길"
        )
    )

    val mockUpcomingTravel = listOf<UpcomingTravel>(
        UpcomingTravel(
            upcomingTravelImg = "https://img1.newsis.com/2024/01/30/NISI20240130_0001470501_web.jpg",
            upcomingTravelDday = "D-39",
            upcomingTravelName = "대전",
            upcomingTravelPeriod = "2024.12.04~2024.12.09 (3박 4일)"
        ),
        UpcomingTravel(
            upcomingTravelImg = "https://files.ban-life.com/content/2022/05/body_1651588720.jpg",
            upcomingTravelDday = "D-120",
            upcomingTravelName = "포천",
            upcomingTravelPeriod = "2025.03.05~2025.04.10 (4박 5일)"
        ),
        UpcomingTravel(
            upcomingTravelImg = "https://www.outdoornews.co.kr/news/photo/202201/33017_94277_222.jpg",
            upcomingTravelDday = "D-182",
            upcomingTravelName = "전주",
            upcomingTravelPeriod = "2025.05.06~2025.05.08 (2박 3일)"
        )
    )

    val mockPreviousTravel = listOf<PreviousTravel>(
        PreviousTravel(
            previousTravelImg = "https://cdn.telltrip.com/news/photo/202404/1493_8132_3630.jpg",
            previousTravelName = "포항",
            previousTravelPeriod = "2024.01.01~2022.01.04 (3박 4일)"
        ),
        PreviousTravel(
            previousTravelImg = "https://cdn.travie.com/news/photo/202404/52446_32969_4728.jpg",
            previousTravelName = "부산",
            previousTravelPeriod = "2023.02.07~2022.02.18 (10박 11일)"
        ),
        PreviousTravel(
            previousTravelImg = "https://img1.yna.co.kr/photo/cms/2015/11/13/01/C0A8CA3C00000150FFD6A6D900026FDB_P2.jpeg",
            previousTravelName = "대구",
            previousTravelPeriod = "2022.01.03~2022.01.06 (3박 4일)"
        )
    )
}