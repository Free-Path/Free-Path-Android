package com.freepath.freepath.presentation.plan

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardBackspace
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.freepath.freepath.presentation.common.FirstTimeLaunchEffect
import com.freepath.freepath.presentation.common.showToast
import com.freepath.freepath.presentation.model.Plan
import com.freepath.freepath.presentation.model.PlanDetail
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.Align
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerDefaults
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberMarkerState
import kotlinx.coroutines.launch

@Composable
fun PlanScreen(
    planId: Int,
    viewModel: PlanViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onClickPlanItem: (PlanDetail) -> Unit,
    onClickChangePlan: () -> Unit,
    onClickBack: () -> Unit,
) {
    FirstTimeLaunchEffect(Unit) {
        viewModel.updatePlanId(planId)
    }
    val plan by viewModel.plan.collectAsStateWithLifecycle()
    PlanScreen(
        plan,
        modifier,
        onClickPlanItem,
        onClickChangePlan,
        onClickBack = onClickBack,
        onClickDelete = viewModel::deletePlan
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlanScreen(
    plan: Plan?,
    modifier: Modifier = Modifier,
    onClickPlanItem: (PlanDetail) -> Unit = {},
    onClickChangePlan: () -> Unit = {},
    onClickBack: () -> Unit = {},
    onClickDelete: () -> Unit = {},
) {
    val sheetState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var isStartReached by remember { mutableStateOf(true) }
    val isExpanded by remember(sheetState.bottomSheetState.targetValue) {
        derivedStateOf {
            sheetState.bottomSheetState.currentValue == SheetValue.Expanded
        }
    }
    Box(modifier) {
        BottomSheetScaffold(
            sheetContainerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier,
            scaffoldState = sheetState,
            sheetContent = {
                if (plan == null) {
                    Text(
                        "데이터가 없습니다.",
                        modifier = modifier.fillMaxSize(),
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                } else {
                    PlanColumn(
                        planDates = plan.planDates,
                        dates = plan.dates,
                        onClickPlanDetail = onClickPlanItem,
                        onClickChangePlan = onClickChangePlan,
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .fillMaxWidth()
                    ) { bool ->
                        isStartReached = bool
                    }
                }
            },
            sheetPeekHeight = 120.dp,
            content = { innerPadding ->
                MapContent(
                    onMarkerClick = { detail ->
                        onClickPlanItem(detail)
                    },
                    onMapClick = {
                        coroutineScope.launch {
                            sheetState.bottomSheetState.partialExpand()
                        }
                    },
                    modifier = Modifier.padding(innerPadding),
                    planDetails = plan?.details ?: emptyList()
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
        PlanTopBar(onClickBack, onClickDelete)
    }
}

@Composable
fun PlanTopBar(
    onClickBack: () -> Unit = {},
    onClickDelete: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClickBack, colors = IconButtonDefaults.filledTonalIconButtonColors()) {
            Icon(Icons.AutoMirrored.Filled.KeyboardBackspace, "뒤로가기")
        }
        Box {
            IconButton(
                { expanded = true },
                colors = IconButtonDefaults.filledTonalIconButtonColors()
            ) {
                Icon(Icons.Rounded.Menu, "뒤로가기")
            }
            DropdownMenu(expanded, { expanded = false }) {
                DropdownMenuItem(
                    { Text("계획 삭제") },
                    onClick = onClickDelete,
                    leadingIcon = { Icon(Icons.Rounded.Delete, "제거", tint = MaterialTheme.colorScheme.error) }
                )
            }
        }
    }
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
    planDetails: List<PlanDetail>,
    onMarkerClick: (PlanDetail) -> Unit = {},
    onMapClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val positions by remember(planDetails) {
        derivedStateOf {
            planDetails.map { LatLng(it.latLng.latitude, it.latLng.longitude) }
        }
    }
    val markerStates = positions.map { rememberMarkerState(position = it) }
    NaverMap(
        modifier = modifier.fillMaxSize(),
        onSymbolClick = {
            context.showToast(
                "Symbol Clicked: ${it.caption} at ${it.position.latitude}, ${it.position.longitude}",
            )
            true
        },
        onMapClick = { _, _ -> onMapClick() },
        content = {
            for ((index, detail) in planDetails.withIndex()) {
                Marker(
                    state = markerStates[index],
                    captionText = detail.title,
                    icon = MarkerDefaults.Icon,
                    onClick = {
                        onMarkerClick(planDetails[index])
                        true
                    },
                    captionAligns = arrayOf(Align.Top, Align.Right)
                )
            }
        },
        uiSettings = MapUiSettings(isZoomControlEnabled = false)
    )
}