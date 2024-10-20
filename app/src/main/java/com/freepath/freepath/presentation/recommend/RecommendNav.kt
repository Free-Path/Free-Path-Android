package com.freepath.freepath.presentation.recommend

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.freepath.freepath.presentation.recommend.subscreen.RecommendCalendarScreen
import com.freepath.freepath.presentation.recommend.subscreen.RecommendDisabilityScreen
import com.freepath.freepath.presentation.recommend.subscreen.RecommendStyleScreen
import com.freepath.freepath.presentation.recommend.subscreen.RecommendTargetScreen
import com.freepath.freepath.presentation.recommend.subscreen.RecommendTogetherScreen
import kotlinx.serialization.Serializable

@Composable
fun RecommendNav(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    recommendViewModel: RecommendViewModel = hiltViewModel(),
    onClickBack: () -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = RecommendNavItem.Calendar,
        modifier = modifier
    ) {
        composable<RecommendNavItem.Calendar> {
            RecommendCalendarScreen(
                viewModel = recommendViewModel,
                onClickNext = { navController.navigate(RecommendNavItem.Together) }
            )
        }
        composable<RecommendNavItem.Together> {
            RecommendTogetherScreen(
                viewModel = recommendViewModel,
                onClickNext = { navController.navigate(RecommendNavItem.Disability) }
            )
        }
        composable<RecommendNavItem.Disability> {
            RecommendDisabilityScreen(
                viewModel = recommendViewModel,
                onClickNext = { navController.navigate(RecommendNavItem.Target) }
            )
        }
        composable<RecommendNavItem.Target> {
            RecommendTargetScreen(
                viewModel = recommendViewModel,
                onClickNext = { navController.navigate(RecommendNavItem.Style) }
            )
        }
        composable<RecommendNavItem.Style> {
            RecommendStyleScreen(
                viewModel = recommendViewModel,
                onClickNext = { navController.navigate(RecommendNavItem.Wait) }
            )
        }
        composable<RecommendNavItem.Wait> {
            RecommendWaitScreen(
                viewModel = recommendViewModel,
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
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    println("${destination.parent} ${destination.route} ${destination.parent?.route}")
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return hiltViewModel(parentEntry)
}