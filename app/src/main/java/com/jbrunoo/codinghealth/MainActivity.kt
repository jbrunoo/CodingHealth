package com.jbrunoo.codinghealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jbrunoo.codinghealth.DogImage.BULLDOG
import com.jbrunoo.codinghealth.DogImage.GOLDEN_RETRIEVER
import com.jbrunoo.codinghealth.DogImage.WEIMARANR
import com.jbrunoo.codinghealth.ui.theme.CodingHealthTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodingHealthTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    var currentImage by rememberSaveable {
        mutableStateOf(BULLDOG)
    }
    val scope = rememberCoroutineScope()
    var isClickable by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Crossfade(
            targetState = currentImage,
            animationSpec = tween(2000),
            label = ""
        ) { selectedImage ->
            Image(
                painter = painterResource(
                    id = selectedImage.resource,
                ), contentDescription = context.getString(selectedImage.label),
                modifier = Modifier.size(300.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = {
                if (isClickable) {
                    currentImage = changeCurrentImage(currentImage)
                    isClickable = false

                    scope.launch {
                        delay(2000)
                        isClickable = true
                    }
                }
            }, enabled = isClickable
        ) {
            Text(text = "다음", fontSize = 20.sp)
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}

enum class DogImage(@DrawableRes val resource: Int, @StringRes val label: Int) {
    BULLDOG(R.drawable.dog1, R.string.bulldog),
    GOLDEN_RETRIEVER(R.drawable.dog2, R.string.golden_retriever),
    WEIMARANR(R.drawable.dog3, R.string.weimaraner)
}

fun changeCurrentImage(currentImage: DogImage): DogImage {
    return when (currentImage) {
        BULLDOG -> GOLDEN_RETRIEVER
        GOLDEN_RETRIEVER -> WEIMARANR
        WEIMARANR -> BULLDOG
    }
}