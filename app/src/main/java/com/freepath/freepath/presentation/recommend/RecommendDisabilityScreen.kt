package com.freepath.freepath.presentation.recommend

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.freepath.freepath.R

@Composable
fun RecommendDisabilityScreen(
    modifier: Modifier = Modifier,
    viewModel: RecommendViewModel = hiltViewModel(),
    onClickNext: () -> Unit,
) {
    val checkedList = remember { viewModel.disabilityCheckList }
    RecommendDisabilityScreen(checkedList, modifier, onClickNext, viewModel::changeDisabilityChecked)
}

@Composable
fun RecommendDisabilityScreen(
    checkedList: List<Boolean>,
    modifier: Modifier = Modifier,
    onClickNext: () -> Unit,
    onClickCheck: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            Text("이런 도움이 필요해요.", fontSize = 24.sp)
            Spacer(Modifier.height(8.dp))
            for ((index, checked) in checkedList.withIndex()) {
                CheckBoxRow(index, checked,
                    Modifier
                        .fillMaxWidth()
                        .clickable { onClickCheck(index) })
            }
        }
        Button(
            onClick = onClickNext,
            Modifier
                .weight(0.3f)
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally)
                .wrapContentHeight()
        ) {
            Text("다음", Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
private fun CheckBoxRow(
    index: Int,
    checked: Boolean,
    modifier: Modifier = Modifier,
) {
    val disabilities = stringArrayResource(R.array.disabilities)
    val text = disabilities.getOrNull(index) ?: return
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

/*
wheelchair	string	휠체어
exit	string	출입통로
elevator	string	엘리베이터
restroom	string	화장실
guidesystem	string	유도안내설비
blindhandicapetc	string	시각장애
signguide	string	수화안내
videoguide	string	자막
hearingroom	string	객실
hearinghandicapetc	string	청각장애
stroller	string	유모차
lactationroom	string	수유실
babysparechair	string	유아용보조의자
infantsfamilyetc	string	영유아가족
auditorium	string	관람석
room	string	객실
handicapetc	string	지체장애
braileblock	string	점자블록
helpdog	string	보조견동반
guidehuman	string	안내요원
audioguide	string	오디오가이드
bigprint	string	큰활자
brailepromotion	string	점자홍보물
contentid	string	콘텐츠ID
parking	string	주차여부
route	string	대중교통
publictransport	string	접근로
ticketoffice	string	매표소
promotion	string	홍보물
 */