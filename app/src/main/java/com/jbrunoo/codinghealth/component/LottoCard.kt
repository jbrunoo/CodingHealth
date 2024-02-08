package com.jbrunoo.codinghealth.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jbrunoo.codinghealth.R

@Composable
fun LottoCard(publishedDate: String, lottoNumbersList: List<List<Int>>) {
    Card(
        modifier = Modifier
            .height(440.dp)
            .padding(horizontal = 32.dp)
            .border(1.dp, Color.DarkGray, RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .weight(0.92f)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CardContent(publishedDate = publishedDate, lottoNumbersList)
            }
            Column(
                modifier = Modifier
                    .weight(0.08f)
                    .fillMaxSize()
                    .background(color = Color(0xFFFFA4A4))
            ) {
                // 90도 회전 텍스트 stackoverflow 참고함
                // 회전시 Column과 weight 때문에 텍스트 영역이 다르게 배치됨. 일단 패스
//                Text(
//                    text = "LottoGenerator",
//                    modifier = Modifier.rotate(90f).vertical().fillMaxSize().background(Color.Gray), // background로 텍스트 범위 확인
//                    color = Color.White
//                )
            }

        }
    }
}

// 차후 string resource 추출
@Composable
fun CardContent(publishedDate: String, lottoNumbersList: List<List<Int>>) {
    Text(text = "Lotto Generator", fontSize = 20.sp)
    Spacer(modifier = Modifier.height(12.dp))
    Text(text = "제 0000 회")
    Spacer(modifier = Modifier.height(12.dp))
    Text("발 행 일 : $publishedDate", fontSize = 14.sp)
    Text("0000 0000 0000 0000 0000", fontSize = 16.sp)
    Spacer(modifier = Modifier.height(4.dp))
    DotLine()
    LottoNumbersListRow(lottoNumbersList)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DotLine()
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("금 액")
                Text(text = "₩ ${lottoNumbersList.size},000원")
            }
            Text("0000 0000 0000 0000 0000", fontSize = 16.sp)
            Image(
                painter = painterResource(id = R.drawable.barcode),
                contentDescription = "barcode",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun LottoNumbersListRow(lottoNumbersList: List<List<Int>>) {
    lottoNumbersList.forEach { lottoNumbers ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "자  동", fontWeight = FontWeight.ExtraBold)
            lottoNumbers.forEach { lottoNumber ->
                Text(
                    text = String.format("%02d", lottoNumber),
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}