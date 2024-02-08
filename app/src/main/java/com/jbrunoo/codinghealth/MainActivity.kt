package com.jbrunoo.codinghealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import com.jbrunoo.codinghealth.component.LottoCard
import com.jbrunoo.codinghealth.component.PurchaseRow
import com.jbrunoo.codinghealth.ui.theme.CodingHealthTheme
import com.jbrunoo.codinghealth.util.getPublishedTime
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
    var selectedTicket by remember { mutableStateOf(1) }
    var publishedDate by remember { mutableStateOf("yyyy/MM/dd HH:mm:ss") }

    Box(
        modifier = Modifier.padding(innerPadding)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PurchaseRow(onTicketChanged = { selectedTicket = it }) {
                lottoNumbersList = List(selectedTicket) {
                    List(6) { Random.nextInt(1, 46) }
                }.flatten().chunked(6)
                publishedDate = getPublishedTime()
            }
            Spacer(modifier = Modifier.height(40.dp))
            LottoCard(publishedDate, lottoNumbersList)
        }
    }
}