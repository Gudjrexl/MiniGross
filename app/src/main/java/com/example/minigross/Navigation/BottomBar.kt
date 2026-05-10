package com.example.minigross.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(
    navController: NavHostController
) {

    val items = listOf(

        BottomNavItem(
            route = Screen.Home.route,
            title = "Home",
            icon = Icons.Default.Home
        ),

        BottomNavItem(
            route = Screen.Cart.route,
            title = "Cart",
            icon = Icons.Default.ShoppingCartCheckout
        ),

        BottomNavItem(
            route = Screen.MyOrder.route,
            title = "My Orders",
            icon = Icons.Default.ShoppingBag
        )
    )

    val navBackStackEntry =
        navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry.value
            ?.destination
            ?.route

    NavigationBar {

        items.forEach { item ->

            NavigationBarItem(

                selected =
                    currentRoute == item.route,

                onClick = {

                    navController.navigate(item.route) {

                        popUpTo(
                            navController.graph.startDestinationId
                        ) {
                            saveState = true
                        }

                        launchSingleTop = true

                        restoreState = true
                    }
                },

                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },

                label = {
                    Text(item.title)
                }
            )
        }
    }
}


data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)