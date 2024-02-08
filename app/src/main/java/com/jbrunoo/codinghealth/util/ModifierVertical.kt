package com.jbrunoo.codinghealth.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

// 90도 회전 텍스트 stackoverflow 참고함
fun Modifier.vertical() =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.height, placeable.width) {
            placeable.place(
                x = -(placeable.width / 2 - placeable.height / 2),
                y = -(placeable.height / 2 - placeable.width / 2)
            )
        }
    }