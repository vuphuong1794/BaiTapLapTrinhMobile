package com.example.tuan3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tuan3.ui.theme.BaiTapMobileTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaiTapMobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun AppNavigation(modifier: Modifier) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "navigation"
        ) {
            composable("navigation") {
                navigation(navController = navController)
            }
            composable("page1") {
                page1(navController = navController)
            }
            composable("page2") {
                page2(modifier = modifier, navController = navController)
            }
            composable("page3") {
                TextDetailScreen(navController = navController)
            }
            composable("page4") {
                ImagesPage(navController = navController)
            }
            composable("page5") {
                textField(navController = navController)
            }
            composable("page6") {
                RowLayoutPage(navController = navController)
            }
            composable("page7") {
                ColumnLayoutPage(navController = navController)
            }
            composable("lazyCol") {
                lazyColumnPage(navController = navController)
            }
            composable("trang3") {
                trang3(navController = navController)
            }
        }
    }
}

