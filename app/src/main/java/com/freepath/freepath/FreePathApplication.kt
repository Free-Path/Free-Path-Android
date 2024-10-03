package com.freepath.freepath

import android.app.Application
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FreePathApplication:Application() {
    override fun onCreate() {
        super.onCreate()
//        NaverMapSdk.getInstance(this).client =
//            NaverMapSdk.NaverCloudPlatformClient("")
    }
}