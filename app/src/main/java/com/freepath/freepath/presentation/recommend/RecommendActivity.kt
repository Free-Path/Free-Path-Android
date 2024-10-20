package com.freepath.freepath.presentation.recommend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.freepath.freepath.ui.theme.FreePathTheme

class RecommendActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreePathTheme {
                RecommendScreen()
            }
        }
    }
}