package com.freepath.freepath.presentation.recommend

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Route
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.freepath.freepath.presentation.common.FirstTimeLaunchEffect
import com.freepath.freepath.presentation.common.icon.Robot

@Composable
fun RecommendWaitScreen(
    modifier: Modifier = Modifier,
    viewModel: RecommendViewModel = hiltViewModel(),
    goPlanScreen: (Int?) -> Unit = {},
) {
    FirstTimeLaunchEffect {
        viewModel.getRecommendPlan()
    }
    val createdId by remember { viewModel.createdId }
    RecommendWaitScreen(createdId, modifier, goPlanScreen)
}

@Composable
fun RecommendWaitScreen(
    isCreationComplete: Int?,
    modifier: Modifier = Modifier,
    goPlanScreen: (Int?) -> Unit,
) {
    val transition = updateTransition(isCreationComplete, label = "selected state")
    transition.AnimatedContent { creationState ->
        when {
            creationState == null -> {
                Column(
                    modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            Modifier.size(80.dp)
                        )
                        Icon(
                            Icons.Rounded.Robot, null,
                            Modifier.size(44.dp)
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Free-Path가\n추천 일정을 만들고 있어요...",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        textAlign = TextAlign.Center
                    )
                }
            }

            creationState >= 0 -> {
                Column(
                    modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Route,
                        contentDescription = null, // contentDescription 추가
                        modifier = Modifier
                            .clip(ShapeDefaults.ExtraLarge)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { goPlanScreen(creationState) }
                            .padding(12.dp)
                            .size(40.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "여행 계획이 만들어졌어요",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                }
            }
        }
    }
}
