package com.example.minigross

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.minigross.Model.DataStoreManager
import com.example.minigross.Navigation.AppNavigation
import com.example.minigross.Presentation.Login
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch{
            var phone = DataStoreManager.getUserPhone(this@MainActivity)

            setContent {
                val navController = rememberNavController()
//                    home page
                        AppNavigation(
                            isLoggedIn = !phone.isNullOrEmpty()
                        )


            }



        }

    }
}

