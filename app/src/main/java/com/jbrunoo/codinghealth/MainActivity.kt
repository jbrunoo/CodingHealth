package com.jbrunoo.codinghealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.jbrunoo.codinghealth.data.local.TodoDatabase
import com.jbrunoo.codinghealth.ui.theme.CodingHealthTheme
import com.jbrunoo.codinghealth.ui.theme.screen.TodoScreen
import com.jbrunoo.codinghealth.vm.TodoViewModel

class MainActivity : ComponentActivity() {
    private val db by lazy {
        TodoDatabase.getDatabase(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodingHealthTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoScreen(todoViewModel = TodoViewModel(db.todoDao()))
                }
            }
        }
    }
}
