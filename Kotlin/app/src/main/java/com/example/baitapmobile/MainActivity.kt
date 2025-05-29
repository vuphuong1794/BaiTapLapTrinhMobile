package com.example.baitapmobile

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
import com.example.baitapmobile.tuan3.ColumnLayoutPage
import com.example.baitapmobile.tuan3.ImagesPage
import com.example.baitapmobile.tuan3.RowLayoutPage
import com.example.baitapmobile.tuan3.TextDetailScreen
import com.example.baitapmobile.tuan3.page1
import com.example.baitapmobile.tuan3.page2
import com.example.baitapmobile.tuan3.textField
import com.example.baitapmobile.tuan4.detailPage
import com.example.baitapmobile.tuan4.homework.thuchanh2.OnBoardScreen
import com.example.baitapmobile.tuan4.homework.thuchanh2.startScreen
import com.example.baitapmobile.ui.theme.BaiTapMobileTheme
import com.example.baitapmobile.tuan4.lazyColumnPage
import com.example.baitapmobile.tuan4.navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaiTapMobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
//                    UserProfileScreen(modifier = Modifier.padding(innerPadding))
//                    UserProfile(modifier = Modifier.padding(innerPadding))
//                    checkEmail(modifier = Modifier.padding(innerPadding))
//                    InputInfo()
//                    UserInfoForm()
//                    basicUI()
                }
            }
        }
    }

    @Composable
    fun AppNavigation(modifier: Modifier) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "startScreen"
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
            composable("detail") {
                detailPage(navController = navController)
            }
            composable("startScreen") {
                startScreen(navController = navController)
            }
            composable("getStartedFirst") {
                OnBoardScreen(navController = navController)
            }
        }
    }
}

