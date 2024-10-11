package com.freepath.freepath.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.freepath.freepath.presentation.plan.PlanScreen
import com.freepath.freepath.ui.theme.FreePathTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreePathTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PlanScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}