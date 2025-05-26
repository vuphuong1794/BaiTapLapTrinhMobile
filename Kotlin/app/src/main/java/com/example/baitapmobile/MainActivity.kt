package com.example.baitapmobile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baitapmobile.ui.theme.BaiTapMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaiTapMobileTheme {
                //val navHostController = rememberNavController()
//                NavHost(navHostController, startDestination = "home") {
//                    composable("home"){
//                        jetpack(navHostController)
//                    }
//                }
                //jetpack(navHostController)


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    UserProfileScreen(modifier = Modifier.padding(innerPadding))
//                    UserProfile(modifier = Modifier.padding(innerPadding))
//                    checkEmail(modifier = Modifier.padding(innerPadding))
//                    InputInfo()
//                    UserInfoForm()
//                    basicUI()
                    jetpack(modifier = Modifier.padding(innerPadding))
                }

            }
        }
    }
}

