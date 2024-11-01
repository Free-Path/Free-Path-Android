package com.freepath.freepath.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.freepath.freepath.R
import com.freepath.freepath.presentation.common.PrepareAlert
import com.freepath.freepath.presentation.common.navigateToPlanActivity
import com.freepath.freepath.presentation.home.HomeScreen
import com.freepath.freepath.presentation.recommend.RecommendNav
import com.freepath.freepath.presentation.travel.TravelScreen
import com.freepath.freepath.ui.theme.FreePathTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            FreePathTheme {
                MainScreenView()
            }
        }
    }
}

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigation(navController = navController) }
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
        ) {
            NavigationGraph(navController = navController)
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf<BottomNavItem>(
        BottomNavItem.Home,
        BottomNavItem.Travel,
        BottomNavItem.Welfare
    )

    androidx.compose.material.BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color(0xFF3F414E)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.navIcon),
                        contentDescription = stringResource(id = item.navTitle),
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                label = { Text(stringResource(id = item.navTitle), fontSize = 9.sp) },
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.DarkGray,
                selected = currentRoute == item.screenRoute,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    val context = navController.context
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screenRoute) {
        composable(BottomNavItem.Home.screenRoute) {
            HomeScreen(isInProgressTravel = true)
        }
        composable(BottomNavItem.Travel.screenRoute) {
            TravelScreen(
                onClickFloating = {
                    navController.navigate("recommend")
                }
            )
        }
        composable(BottomNavItem.Welfare.screenRoute) {
            var showAlert by remember { mutableStateOf(true) }
            PrepareAlert(showAlert){
                navController.popBackStack()
                showAlert = false
            }
        }
        composable("recommend") {
            RecommendNav(finishNav = { id ->
                navController.navigate(BottomNavItem.Travel.screenRoute) {
                    popUpTo(BottomNavItem.Travel.screenRoute)
                }
                if (id != null) {
                    navigateToPlanActivity(context, 10)
                }
            })
        }
    }
}


sealed class BottomNavItem(
    val navTitle: Int,
    val navIcon: Int,
    val screenRoute: String,
) {
    data object Home : BottomNavItem(
        navTitle = R.string.bnv_home,
        navIcon = R.drawable.ic_bnv_home_32,
        screenRoute = "Home"
    )

    data object Travel : BottomNavItem(
        navTitle = R.string.bnv_travel,
        navIcon = R.drawable.ic_bnv_travel_32,
        screenRoute = "Travel"
    )

    data object Welfare : BottomNavItem(
        navTitle = R.string.bnv_welfare,
        navIcon = R.drawable.ic_bnv_welfare_32,
        screenRoute = "Welfare"
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FreePathTheme {
        MainScreenView()
    }
}