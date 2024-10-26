package com.freepath.freepath.presentation.plan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.freepath.freepath.ui.theme.FreePathTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val planId = intent.getIntExtra(PLAN_ID, -1)
        if (planId < 0) finishAffinity()
        setContent {
            FreePathTheme {
                Scaffold { innerPadding ->
                    PlanNav(planId, Modifier.padding(innerPadding))
                }
            }
        }
    }

    companion object {
        const val PLAN_ID = "PLAN_ID"
    }
}