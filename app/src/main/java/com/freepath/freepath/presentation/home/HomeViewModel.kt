package com.freepath.freepath.presentation.home

import androidx.lifecycle.ViewModel
import com.freepath.freepath.presentation.model.RecommendedTouristSpot
import com.freepath.freepath.presentation.model.CurrentTravel

class HomeViewModel : ViewModel() {
    val mockCurrentTravel = CurrentTravel(
        currentTravelImg = "https://san.chosun.com/news/photo/201911/13272_55951_153.jpg",
        currentTravelTitle = "제주도",
        currentTravelPeriod = "2024.11.04~2024.12.07 (3박 4일)",
        currentTravelRoute = listOf(
            "· 1일차: 섭지코지, 성산일출봉",
            "· 2일차: 만장굴, 성읍민속마을",
            "· 3일차: 우도, 추자도, 국립제주박물관",
            "· 4일차: 제주 올레길"
        )
    )

    val mockRecommendedTouristSpot = listOf(
        RecommendedTouristSpot(
            imageUrl = "https://www.cha.go.kr/unisearch/images/national_treasure/2021042208550900.jpg",
            placeName = "석굴암"
        ),
        RecommendedTouristSpot(
            imageUrl = "https://www.sangjomagazine.com/imgdata/sangjomagazine_com/202009/2020092936542609.jpg",
            placeName = "경복궁"
        ),
        RecommendedTouristSpot(
            imageUrl = "https://news.kbs.co.kr/data/news/2017/11/07/3568055_9Np.jpg",
            placeName = "창경궁 대온실"
        ),
        RecommendedTouristSpot(
            imageUrl = "https://hangang.seoul.go.kr/www/file/smEditorImage.do?fileDay=202306&fileName=202306161823060d71cc4644004689a1b99972ba8eb859.jpg",
            placeName = "반포한강공원"
        ),
        RecommendedTouristSpot(
            imageUrl = "https://junglim.info/wp-content/uploads/p-xt-11_72dpi-2.png",
            placeName = "국립중앙박물관"
        ),
        RecommendedTouristSpot(
            imageUrl = "https://www.agoda.com/wp-content/uploads/2019/03/N-Seoul-Tower-Namsan-Cable-Car.jpg",
            placeName = "N서울타워"
        ),
        RecommendedTouristSpot(
            imageUrl = "https://mbs.mapo.go.kr/mapoapp/att_img/RBCJUCQG8U8W5NNFI20R.jpg",
            placeName = "매봉산 무장애숲길"
        ),
    )
}