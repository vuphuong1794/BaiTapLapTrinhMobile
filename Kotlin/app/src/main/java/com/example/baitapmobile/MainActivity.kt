package com.example.baitapmobile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.baitapmobile.tuan3.ColumnLayoutPage
import com.example.baitapmobile.tuan3.ImagesPage
import com.example.baitapmobile.tuan3.RowLayoutPage
import com.example.baitapmobile.tuan3.TextDetailScreen
import com.example.baitapmobile.tuan3.page1
import com.example.baitapmobile.tuan3.page2
import com.example.baitapmobile.tuan3.textField
import com.example.baitapmobile.tuan4.detailPage
import com.example.baitapmobile.tuan4.homework.baitap1.qlyThuVien
import com.example.baitapmobile.tuan4.homework.baitap2.DataFlowNavigation
import com.example.baitapmobile.tuan4.homework.baitap2.DataFlowNavigation2
import com.example.baitapmobile.tuan4.homework.baitap2.DataFlowNavigation3
import com.example.baitapmobile.tuan4.homework.baitap2.DataFlowNavigation4
import com.example.baitapmobile.tuan4.homework.thuchanh2.OnBoardScreen
import com.example.baitapmobile.tuan4.homework.thuchanh2.startScreen
import com.example.baitapmobile.ui.theme.BaiTapMobileTheme
import com.example.baitapmobile.tuan4.lazyColumnPage
import com.example.baitapmobile.tuan4.navigation
import com.example.baitapmobile.tuan5.bai2.LoginForm
import com.example.baitapmobile.tuan5.bai1.ProductDetailScreen
import com.example.baitapmobile.tuan5.bai2.GoogleAuthUiClient
import com.example.baitapmobile.tuan5.bai2.SignInViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import com.example.baitapmobile.tuan5.bai2.ProfileScreen


class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

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
            startDestination = "loginForm"
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
            composable("qlyThuVien") {
                qlyThuVien(navController = navController)
            }

            //bai 2
            composable("dataFlow") {
                DataFlowNavigation(null, null, navController)
            }
            composable("navDataFlow2/{email}",
                arguments = listOf(navArgument("email") {
                    type = NavType.StringType
                })
            ){ backStackEntry ->
                val email = backStackEntry.arguments?.getString("email")
                if (email != null) {
                    DataFlowNavigation2(email, navController)
                }
            }
            composable("navDataFlow3/{email}/{otp}",
                arguments = listOf(
                    navArgument("otp") {
                        type = NavType.StringType
                    }
                    ,navArgument("email") {
                        type = NavType.StringType
                    }
                )
            ) {backStackEntry ->
                val email = backStackEntry.arguments?.getString("email")
                val otp = backStackEntry.arguments?.getString("otp")
                if (email!=null && otp != null) {
                    DataFlowNavigation3(email,otp, navController)
                }
            }

            composable("navDataFlow4/{email}/{otp}/{pass}",
                arguments = listOf(navArgument("pass") {
                    type = NavType.StringType
                })
            ) {backStackEntry ->
                val email = backStackEntry.arguments?.getString("email")
                val otp = backStackEntry.arguments?.getString("otp")
                val pass = backStackEntry.arguments?.getString("pass")
                DataFlowNavigation4(email,otp,pass, navController)

            }

            composable("navDataFlow5/{email}/{pass}",
                arguments = listOf(
                    navArgument("email") {
                        type = NavType.StringType
                    },
                    navArgument("pass") {
                        type = NavType.StringType
                    }
                    )
                ) {
                backStackEntry ->
                    val email = backStackEntry.arguments?.getString("email")
                    val pass = backStackEntry.arguments?.getString("pass")
                    DataFlowNavigation(email, pass, navController)
            }

            //tuan 5
            composable("productScreenPage") {
                ProductDetailScreen(navController)
            }

            composable("loginForm") {
                val viewModel = viewModel<SignInViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = {result ->
                        if(result.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                                val signInResult = googleAuthUiClient.SignInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                viewModel.onSigninResult(signInResult)
                            }
                        }
                    }
                )

                LaunchedEffect(key1 = state.isSignSuccessful) {
                    if(state.isSignSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Sign in successful",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate("user_profile")
                    }
                }

                LoginForm(
                    navController,
                    state = state,
                    onSignInClick = {
                        lifecycleScope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    }
                )
            }

            composable("user_profile") {
                ProfileScreen(
                    navController,
                    userData = googleAuthUiClient.getSignInUser(),
                    onSignOut = {
                        lifecycleScope.launch {
                            googleAuthUiClient.SignOut()
                            Toast.makeText(
                                applicationContext,
                                "Signed out",
                                Toast.LENGTH_LONG
                            ).show()

                            navController.navigate("loginForm") {
                                popUpTo("loginForm") { inclusive = true }
                            }
                        }
                    }
                )
            }
        }
    }
}

