package com.jbrunoo.codinghealth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jbrunoo.codinghealth.ui.screen.HomeScreen
import com.jbrunoo.codinghealth.ui.screen.SecondScreen
import com.jbrunoo.codinghealth.ui.screen.ThirdScreen

// NavHostController vs NavController
@Composable
fun NavHostConfiguration(navController: NavHostController, paddingValues: PaddingValues) {
    // 처음에 여기서 rememberNavController()를 초기화하고 mainActivity에서 사용해서
    // navigationBar 사용 시 scaffold 계속 호출함
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Main.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(BottomNavItem.Main.route) { HomeScreen() }
        composable(BottomNavItem.Second.route) { SecondScreen() }
        composable(BottomNavItem.Third.route) { ThirdScreen() }
    }
}