package com.freepath.freepath.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.freepath.freepath.presentation.common.icon.MinusCircle
import com.freepath.freepath.ui.theme.FreePathTheme

@Composable
fun NumberPlusMinusButton(
    number: Int,
    modifier: Modifier = Modifier,
    onClickPlus: () -> Unit,
    onClickMinus: () -> Unit,
) {
    Row(
        modifier
            .width(150.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.large),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClickMinus) {
            Icon(Icons.Sharp.MinusCircle, "minus", Modifier.size(28.dp))
        }
        Text(
            number.toString(),
            Modifier
                .padding(horizontal = 8.dp)
                .weight(1f),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
        IconButton(onClickPlus) {
            Icon(Icons.Sharp.AddCircle, "plus", Modifier.size(28.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewNumberPlusMinusButton() {
    FreePathTheme {
        NumberPlusMinusButton(5, Modifier, {}, {})
    }
}