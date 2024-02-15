package com.jbrunoo.codinghealth.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.jbrunoo.codinghealth.R

// @Composable x -> stringResource, context.getString() x
// 1. const val로 구성
// 2. int 파라미터에 R.string.home 등 값을 넘겨서 string 변환하여 사용
// 3. @StringRes annotation (res 값 강제, IDE 표시 x 컴파일 에러)

sealed class BottomNavItem(
    val route: String,
    @StringRes val topTitle: Int,
    val itemIcon: ImageVector,
    @StringRes val label: Int
) {
    companion object {
        const val MAIN = "main"
        const val SECOND = "second"
        const val THIRD = "third"
    }
    object Main : BottomNavItem(MAIN, R.string.home, Icons.Default.Home, R.string.home)
    object Second : BottomNavItem(SECOND, R.string.left, Icons.Default.ArrowBack, R.string.left)
    object Third : BottomNavItem(THIRD, R.string.right, Icons.Default.ArrowForward, R.string.right)
}