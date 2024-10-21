package com.freepath.freepath.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freepath.freepath.ui.theme.FreePathTheme
import com.freepath.freepath.ui.theme.Pink80
import com.freepath.freepath.ui.theme.Pretendard12
import com.freepath.freepath.ui.theme.TitleGray

@Composable
fun RecommendTag(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = Pink80,
    contentColor: Color = TitleGray,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = Pretendard12,
            color = contentColor,
            maxLines = 1,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun RecommendTagPreview() {
    FreePathTheme {
        RecommendTag(
            text = "👨🏻‍🦽‍➡️배리어프리",
        )
    }
}