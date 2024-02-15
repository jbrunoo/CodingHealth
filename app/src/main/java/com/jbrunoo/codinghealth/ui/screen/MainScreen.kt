package com.jbrunoo.codinghealth.ui.screen

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jbrunoo.codinghealth.navigation.BottomNavItem
import com.jbrunoo.codinghealth.navigation.NavHostConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val bottomNavItems = listOf(BottomNavItem.Second, BottomNavItem.Main, BottomNavItem.Third)

    // navigationBarItem의 selected의 boolean 비교를 위한 route
    // currentBackStackEntryAsState 활용(forEachIndexed idx 비교, bottomNavItem route로도 구현 가능)
    val currentRoute = currentRoute(navController)
    // 차후 scope func 정리하기
    val topTitle =
        bottomNavItems.find { it.route == currentRoute }?.topTitle?.let { stringResource(id = it) }
            ?: ""
//    val topTitle2 = stringResource(id = bottomNavItems.find { it.route == currentRoute }?.topTitle ?: 0)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = topTitle) })
        },
        bottomBar = {
            // BottomAppBar / NavigationBar
            NavigationBar {
                BottomNavigation(bottomNavItems, currentRoute, navController)
            }
        }
    ) { paddingValues ->
        NavHostConfiguration(navController, paddingValues)
    }
}

// extract func 기능 사용해봄
@Composable
private fun RowScope.BottomNavigation(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    navController: NavHostController
) {
    bottomNavItems.forEach { item ->
        NavigationBarItem(
            selected = currentRoute == item.route,
            onClick = {
                navController.navigate(item.route) {
                    // 공식문서는 navController.graph.findStartDestination().id
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = item.itemIcon,
                    contentDescription = stringResource(id = item.label)
                )
            },
            label = { Text(text = stringResource(id = item.label)) })
    }
}

// 반환 값 있는 composable 함수는 letterCase
@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}