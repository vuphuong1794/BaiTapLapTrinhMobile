package com.example.baitapmobile.tuan4.homework.thuchanh2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.baitapmobile.R
import kotlinx.coroutines.launch


val sampleData = listOf(
    pageItemModel(
        title = "Easy Time Management",
        desc = "Manage tasks easily with priorities and schedules.",
        image = R.drawable.anh1
    ),
    pageItemModel(
        title = "Increase Work Effectiveness",
        desc = "Track performance and stay productive.",
        image = R.drawable.second
    ),
    pageItemModel(
        title = "Reminder Notification",
        desc = "Reminder Notification",
        image = R.drawable.anh3
    )
)

@Composable
fun OnBoardScreen(navController: NavHostController) {
    val pagerState = rememberPagerState { sampleData.size }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // HorizontalPager chiếm phần lớn màn hình
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Trọng số để nó chiếm phần còn lại
        ) { page ->
            pageItem(page = sampleData[page])
        }

        // Nút điều hướng nằm ngoài pager, luôn hiển thị
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (pagerState.currentPage > 0) {
                Button(onClick = {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                }) {
                    Text("Back")
                }
            }

            Button(onClick = {
                if (pagerState.currentPage < sampleData.lastIndex) {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                } else {
                    navController.navigate("startScreen") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            }) {
                Text(
                    if (pagerState.currentPage == sampleData.lastIndex)
                        "Get Started"
                    else
                        "Next"
                )
            }
        }
    }
}
