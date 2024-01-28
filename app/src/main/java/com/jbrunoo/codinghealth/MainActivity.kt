package com.jbrunoo.codinghealth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.jbrunoo.codinghealth.ui.theme.CodingHealthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val intent2 = Intent(this, SecondActivity::class.java)
//            val intent3 = Intent(this, ThirdActivity::class.java)
            val context = LocalContext.current

            CodingHealthTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            val intent2 = Intent(this@MainActivity, SecondActivity::class.java)
                            startActivity(intent2)
                            finish()
                        }) {
                            Text(text = "2번째 화면")
                        }
                        Button(onClick = {
                            val intent3 = Intent(context, ThirdActivity::class.java)
//                            intent3.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // setFlags -> flags
                            startActivity(intent3)
                            finish()
                        }) {
                            Text(text = "3번째 화면")
                        }
                    }
                }
            }
        }
    }
}
