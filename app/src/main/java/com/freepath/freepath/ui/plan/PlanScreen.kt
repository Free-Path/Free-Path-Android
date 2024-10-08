package com.freepath.freepath.ui.plan

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.freepath.freepath.ui.common.showToast
import com.freepath.freepath.ui.model.PlanDetail
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.Align
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerDefaults
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.util.MarkerIcons
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun PlanScreen(
    viewModel: PlanViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val plan by viewModel.plan.collectAsState()
    val planDetailsList = plan.planDetailsList.toMutableStateList()
    val context = LocalContext.current
    PlanScreen(
        plan.startDate,
        planDetailsList,
        modifier,
        {
            context.showToast("PlanItem Clicked: ${it.title}")
        },
        {
            context.showToast("PlanChange Clicked")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlanScreen(
    startDate: LocalDate,
    planDetailsList: List<List<PlanDetail>> = mutableStateListOf(),
    modifier: Modifier = Modifier,
    onClickPlanItem: (PlanDetail) -> Unit = {},
    onClickPlanChange: () -> Unit = {},
) {
//    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels
    val context = LocalContext.current
    val sheetState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var isStartReached by remember { mutableStateOf(true) }
    val isExpanded by remember(sheetState.bottomSheetState.targetValue) {
        derivedStateOf {
            sheetState.bottomSheetState.currentValue == SheetValue.Expanded
        }
    }

    BottomSheetScaffold(
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier,
        scaffoldState = sheetState,
        sheetContent = {
            PlanColumn(
                plansList = planDetailsList,
                dates = List(planDetailsList.size) { startDate.plusDays(1L + it) },
                onClickPlanDetail = onClickPlanItem,
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
            ) { bool ->
                isStartReached = bool
            }
        },
        sheetPeekHeight = 120.dp,
        content = { innerPadding ->
            MapContent(
                onMarkerClick = {
                    context.showToast("Marker Clicked: ${it.latitude}, ${it.longitude}")
                },
                onMapClick = {
                    coroutineScope.launch {
                        sheetState.bottomSheetState.partialExpand()
                    }
                },
                modifier = Modifier.padding(innerPadding)
            )
        },
        sheetSwipeEnabled = false,
//        sheetSwipeEnabled = isExpanded.not()|| isStartReached,
        sheetDragHandle = {
            BottomSheetDragHandler(isExpanded) {
                coroutineScope.launch {
                    if (isExpanded) {
                        sheetState.bottomSheetState.partialExpand()
                    } else {
                        sheetState.bottomSheetState.expand()
                    }
                }
            }
        }
    )
}

@Composable
private fun BottomSheetDragHandler(
    isExpanded: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.size(width = 120.dp, height = 36.dp),
        contentPadding = PaddingValues(),
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        onClick = onClick,
        content = {
            val rotation by updateTransition(isExpanded, "ordinal").animateFloat(
                label = "StarRotation",
                transitionSpec = {
                    tween(durationMillis = 500, easing = FastOutSlowInEasing)
                }
            ) { ordinal ->
                if (ordinal) 180f else 0f
            }
            Icon(
                Icons.Filled.KeyboardArrowUp,
                "여행 계획 하단 목록 버튼",
                Modifier.rotate(rotation)
            )
        })
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
private fun MapContent(
    modifier: Modifier = Modifier,
    onMarkerClick: (LatLng) -> Unit = {},
    onMapClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val positions = remember { listOf(LatLng(37.57207, 126.97917)) }
    NaverMap(
        modifier = Modifier.fillMaxSize(),
        onSymbolClick = {
            context.showToast(
                "Symbol Clicked: ${it.caption} at ${it.position.latitude}, ${it.position.longitude}",
            )
            true
        },
        onMapClick = { _, _ -> onMapClick() },
        content = {
            var enabled1 by remember { mutableStateOf(true) }
            Marker(
                state = rememberMarkerState(
                    position = positions[0]
                ),
                captionText = "Marker1",
                icon = if (enabled1) {
                    MarkerDefaults.Icon
                } else {
                    MarkerIcons.GRAY
                },
                onClick = {
                    enabled1 = !enabled1
                    onMarkerClick(positions[0])
                    true
                },
                captionAligns = arrayOf(Align.Top, Align.Right)
            )
        },
        uiSettings = MapUiSettings(isZoomControlEnabled = false)
    )
}