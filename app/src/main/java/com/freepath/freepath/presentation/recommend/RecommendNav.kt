package com.freepath.freepath.presentation.recommend

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.freepath.freepath.presentation.recommend.subscreen.RecommendCalendarScreen
import com.freepath.freepath.presentation.recommend.subscreen.RecommendDisabilityScreen
import com.freepath.freepath.presentation.recommend.subscreen.RecommendStyleScreen
import com.freepath.freepath.presentation.recommend.subscreen.RecommendTargetScreen
import com.freepath.freepath.presentation.recommend.subscreen.RecommendThemeScreen
import com.freepath.freepath.presentation.recommend.subscreen.RecommendTogetherScreen
import kotlinx.serialization.Serializable

@Composable
fun RecommendNav(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    recommendViewModel: RecommendViewModel = hiltViewModel(),
    finishNav: (Int?) -> Unit = {},
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = RecommendNavItem.Calendar,
        modifier = modifier
    ) {
        composable<RecommendNavItem.Calendar> {
            RecommendCalendarScreen(
                onClickBack = { finishNav(null) },
                viewModel = recommendViewModel,
                onClickNext = { navController.navigate(RecommendNavItem.Together) }
            )
        }
        composable<RecommendNavItem.Together> {
            RecommendTogetherScreen(
                onClickBack = {
                    navController.navigate(RecommendNavItem.Calendar) {
                        popUpTo(RecommendNavItem.Calendar)
                    }
                },
                viewModel = recommendViewModel,
                onClickNext = { navController.navigate(RecommendNavItem.Disability) }
            )
        }
        composable<RecommendNavItem.Disability> {
            RecommendDisabilityScreen(
                onClickBack = {
                    navController.navigate(RecommendNavItem.Together) {
                        popUpTo(RecommendNavItem.Together)
                    }
                },
                viewModel = recommendViewModel,
                onClickNext = { navController.navigate(RecommendNavItem.Theme) }
            )
        }
        composable<RecommendNavItem.Theme> {
            RecommendThemeScreen(
                onClickBack = {
                    navController.navigate(RecommendNavItem.Disability) {
                        popUpTo(RecommendNavItem.Disability)
                    }
                },
                viewModel = recommendViewModel,
                onClickNext = { navController.navigate(RecommendNavItem.Target) }
            )
        }
        composable<RecommendNavItem.Target> {
            RecommendTargetScreen(
                onClickBack = {
                    navController.navigate(RecommendNavItem.Theme) {
                        popUpTo(RecommendNavItem.Theme)
                    }
                },
                viewModel = recommendViewModel,
                onClickNext = { navController.navigate(RecommendNavItem.Style) }
            )
        }
        composable<RecommendNavItem.Style> {
            RecommendStyleScreen(
                onClickBack = {
                    navController.navigate(RecommendNavItem.Target) {
                        popUpTo(RecommendNavItem.Target)
                    }
                },
                viewModel = recommendViewModel,
                onClickNext = { navController.navigate(RecommendNavItem.Wait) }
            )
        }
        composable<RecommendNavItem.Wait> {
            RecommendWaitScreen(
                viewModel = recommendViewModel,
                goPlanScreen = finishNav
            )
        }
    }
}

sealed class RecommendNavItem {
    @Serializable
    data object Target : RecommendNavItem()

    @Serializable
    data object Style : RecommendNavItem()

    @Serializable
    data object Disability : RecommendNavItem()

    @Serializable
    data object Together : RecommendNavItem()

    @Serializable
    data object Calendar : RecommendNavItem()

    @Serializable
    data object Wait : RecommendNavItem()

    @Serializable
    data object Theme : RecommendNavItem()
}