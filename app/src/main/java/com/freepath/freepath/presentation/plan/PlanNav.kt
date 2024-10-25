package com.freepath.freepath.presentation.plan

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.freepath.freepath.presentation.planchange.PlanChangeScreen
import kotlinx.serialization.Serializable

@Composable
fun PlanNav(
    id: Int,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = PlanNavItem.Main,
    ) {
        composable<PlanNavItem.Main> {
            PlanScreen(planId = id, modifier = modifier)
        }
        composable<PlanNavItem.Change> {
            PlanChangeScreen(
                modifier = modifier,
                onClickCancel = {
                    navController.navigate(PlanNavItem.Main) {
                        popUpTo(PlanNavItem.Main)
                    }
                },
                onClickComplete = {
                    navController.navigate(PlanNavItem.Main) {
                        popUpTo(PlanNavItem.Main)
                    }
                }
            )
        }
    }
}

private sealed interface PlanNavItem {
    @Serializable
    data object Main : PlanNavItem

    @Serializable
    data object Change : PlanNavItem
}