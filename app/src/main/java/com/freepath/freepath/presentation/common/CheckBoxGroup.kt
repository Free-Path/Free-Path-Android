package com.freepath.freepath.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxGroup(
    checkedList: List<Boolean>,
    onClickCheck: (Int) -> Unit,
    textArray: Array<String>,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        for ((index, checked) in checkedList.withIndex()) {
            val text = textArray.getOrNull(index) ?: return
            CheckBoxRow(checked, text,
                Modifier
                    .fillMaxWidth()
                    .clickable { onClickCheck(index) })
        }
    }
}

@Composable
private fun CheckBoxRow(
    checked: Boolean,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            if (checked) Icons.Outlined.CheckBox else Icons.Outlined.CheckBoxOutlineBlank,
            "Check Box",
            Modifier.size(32.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(text)
    }
}