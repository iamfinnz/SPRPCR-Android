package com.example.aplikasipeminjamanruangan.presentation.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.example.aplikasipeminjamanruangan.presentation.utils.textColor

@Composable
fun HomeBottomNavBar(
    navBackStackEntry: NavBackStackEntry?, navController: NavHostController
) {
    val currDestination = navBackStackEntry?.destination
    val color = MaterialTheme.colors.secondary
    BottomNavigation(
        elevation = 32.dp,
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = Modifier.clip(
            RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp,
            )
        )
    ) {
        listOfTabScreen.forEach { screen ->
            AddItem(
                screen = screen, currDestination = currDestination, navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: AppDestination, currDestination: NavDestination?, navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(
                text = screen.text,
                fontSize = if (currDestination?.route == screen.route) 14.sp else 12.sp,
                style = if (currDestination?.route == screen.route) MaterialTheme.typography.h3 else MaterialTheme.typography.h3,
                fontWeight = if (currDestination?.route == screen.route) FontWeight.Bold else FontWeight.Normal,
                color = textColor,
                modifier = Modifier.alpha(if (currDestination?.route == screen.route) 1F else 0.4F)
            )
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = screen.text,
            )
        }, selected = currDestination?.hierarchy?.any {
            it.route == screen.route
        } == true, onClick = {
            navController.navigateSingleTopTo(screen.route)
        }, unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
    )
}