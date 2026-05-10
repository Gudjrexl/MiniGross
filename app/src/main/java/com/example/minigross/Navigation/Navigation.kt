package com.example.minigross.Navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.minigross.Model.DataClass.OrderItem
import com.example.minigross.Presentation.BuyNowScreen
import com.example.minigross.Presentation.HomeScreen
import com.example.minigross.Presentation.Login
import com.example.minigross.Presentation.MyCartScreen
import com.example.minigross.Presentation.MyOrderScreen
import com.example.minigross.Presentation.orderedproduct

sealed class Screen(
    val route: String,
    val title: String
) {

    object Home : Screen(
        "home",
        "Home"
    )

    object Cart : Screen(
        "cart",
        "Cart"
    )

    object MyOrder : Screen(
        "myorder",
        "My Orders"
    )

    object Login : Screen(
        "login",
        "Login"
    )

    object BuyNow : Screen(
        "buynow",
        "Buy Now"
    )
}




@Composable
fun AppNavigation(
    isLoggedIn: Boolean
) {

    val navController = rememberNavController()
    val hideBottomBarRoutes = listOf(
        "myorder/{prodid}" ,
        "buynow/{items}"

    )
    Scaffold(

        bottomBar = {

            val currentBackStackEntry =
                navController.currentBackStackEntryAsState()

            val currentRoute =
                currentBackStackEntry.value
                    ?.destination
                    ?.route

            if (currentRoute != Screen.Login.route &&
                currentRoute !in hideBottomBarRoutes) {

                BottomBar(navController)
            }
        }

    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn)
                Screen.Home.route
            else
                Screen.Login.route,
            modifier = Modifier.padding(

                bottom =

                    if(
                        navController
                            .currentBackStackEntryAsState()
                            .value
                            ?.destination
                            ?.route in hideBottomBarRoutes
                    ) 0.dp

                    else paddingValues.calculateBottomPadding()
            )        ) {

            composable(Screen.Home.route) {
                HomeScreen(navController)
            }




            composable(Screen.Cart.route) {
                MyCartScreen(navController)
            }

            composable(
                route = "myorder/{prodid}"
            ) { backStackEntry ->

                val prodid =
                    backStackEntry.arguments?.getString("prodid")

                MyOrderScreen(
                    prodid = prodid ?: "" ,
                    navController
                )
            }


            composable(
                route = "buynow/{items}"
            ) { backStackEntry ->

                val itemString =
                    backStackEntry.arguments
                        ?.getString("items")
                        ?: ""

                val productList =

                    itemString
                        .split(",")

                        .mapNotNull { item ->

                            val parts = item.split(":")

                            if(parts.size == 2){

                                OrderItem(

                                    prodid =
                                        parts[0].toInt(),

                                    quantity =
                                        parts[1].toInt()
                                )

                            } else null
                        }

                BuyNowScreen(
                    productList = productList,
                    navController
                )
            }

            composable(Screen.Login.route) {
                Login(navController)
            }

            composable(Screen.MyOrder.route) {
                orderedproduct()
            }


        }
    }
}
