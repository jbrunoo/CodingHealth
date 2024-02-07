package com.jbrunoo.codinghealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jbrunoo.codinghealth.ui.theme.CodingHealthTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodingHealthTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = { Text(text = "Lotto Generator") }
                            )
                        }
                    ) { innerPadding ->
                        Content(innerPadding)
                    }
                }
            }
        }
    }
}

@Composable
fun Content(innerPadding: PaddingValues) {
    var lottoNumbersList by remember { mutableStateOf(emptyList<List<Int>>()) }
    var bonusChecked by remember { mutableStateOf(false) }
    var selectedTicket by remember { mutableStateOf(1) }

    Box(
        modifier = Modifier.padding(innerPadding)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectTicketBox() {
                    selectedTicket = it
                }
                IconButton(onClick = {
                    val size = if (bonusChecked) 7 else 6
                    lottoNumbersList = List(selectedTicket) {
                        List(size) { Random.nextInt(1, 46) }
                    }.flatten().chunked(size)
                }) {
                    Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = null)
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            LottoBalls(lottoNumbersList)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "보너스 번호")
                        Checkbox(
                            checked = bonusChecked,
                            onCheckedChange = { bonusChecked = it })
                    }
                }
            }
        }
    }
}

@Composable
fun SelectTicketBox(onTicketChanged: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var ticket by remember { mutableStateOf(1) }

    Box(
        modifier = Modifier
            .width(112.dp)
            .height(40.dp)
            .border(1.dp, Color.LightGray, RectangleShape)
            .clickable { expanded = true },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "${ticket}장")
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            listOf(1, 2, 3, 4, 5).forEach { input ->
                DropdownMenuItem(text = { Text(input.toString()) },
                    onClick = {
                        onTicketChanged(input)
                        ticket = input
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun LottoBalls(lottoNumbersList: List<List<Int>>) {
    lottoNumbersList.forEachIndexed { index, lottoNumbers ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${index + 1}번 :", fontWeight = FontWeight.ExtraBold)
            lottoNumbers.forEach { lottoNumber ->
                val ballColor = getBallColor(lottoNumber)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(ballColor, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$lottoNumber",
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

enum class BallColor(val start: Int, val end: Int, val color: Color) {
    YELLOW(1, 10, Color.Yellow),
    BLUE(11, 20, Color.Blue),
    RED(21, 30, Color.Red),
    GRAY(31, 40, Color.Gray),
    GREEN(41, 45, Color.Green)
}

fun getBallColor(number: Int): Color {
    return BallColor.values().find { number in it.start..it.end }?.color ?: Color.White
}

