package com.freepath.freepath.presentation.plan

import android.app.Activity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = PlanNavItem.Main,
        popExitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable<PlanNavItem.Main> {
            PlanScreen(
                planId = id,
                modifier = modifier,
                onClickPlanItem = {
                    // TODO: 관광지 정보 화면 이동
                },
                onClickChangePlan = {
                    navController.navigate(PlanNavItem.Change)
                },
                onClickBack = {
                    if(navController.popBackStack().not()) {
                        (context as Activity).finish()
                    }
                },
            )
        }
        composable<PlanNavItem.Change> {
            PlanChangeScreen(
                planId = id,
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