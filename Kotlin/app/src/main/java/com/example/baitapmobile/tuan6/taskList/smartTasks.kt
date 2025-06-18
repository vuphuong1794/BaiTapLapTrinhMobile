package com.example.baitapmobile.tuan6.taskList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.example.baitapmobile.R
import com.example.baitapmobile.viewmodel.TaskViewModel

@Composable
fun SmartTasks(navController: NavHostController) {
    val tasksViewModel: TaskViewModel = viewModel(factory = viewModelFactory {
        initializer { TaskViewModel() }
    })

    val taskList by tasksViewModel.taskList.collectAsState()
    val uiState by tasksViewModel.uiState.collectAsState()
    val isLoading by tasksViewModel.isLoading.collectAsState()
    val errorMessage by tasksViewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        tasksViewModel.fetchTasks()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate("addTask") },
                containerColor = Color(0xFF2196F3),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        bottomBar = {
            BottomNavBar(
                selectedItem = 0,
                onItemSelected = {},
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Header Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo2),
                    contentDescription = "UTH logo",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE3F2FD))
                        .padding(8.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "SmartTasks",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2196F3)
                    )
                    Text(
                        text = "A Simple and efficient to-do app",
                        fontSize = 14.sp,
                        color = Color(0xFF2196F3)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.bell),
                    contentDescription = "bell icon",
                    modifier = Modifier.size(24.dp)
                )
            }

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage?.let { error ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                ) {
                    Text(
                        text = "Lỗi: $error",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            if (taskList.isNotEmpty()) {
                Text(
                    text = "Danh sách công việc (${taskList.size})",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(taskList) { task ->
                        TaskItem(
                            task = task,
                            onTaskClick = { navController.navigate("task_detail/${task.id}") },
                            onTaskCheck = {}
                        )
                    }
                }
            } else if (!isLoading && errorMessage == null) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.notask),
                            contentDescription = "no task",
                            modifier = Modifier.size(120.dp)
                        )
                        Text(
                            text = "No Tasks Yet!",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Stay productive - add something to do"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: com.example.baitapmobile.responseModel.Task,
    onTaskClick: () -> Unit = {},
    onTaskCheck: (Boolean) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onTaskClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (task.priority) {
                "High" -> Color(0xFFFFEBEE)
                "Medium" -> Color(0xFFFFF3E0)
                "Low" -> Color(0xFFE8F5E8)
                else -> Color.White
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Checkbox(
                checked = task.status == "Completed",
                onCheckedChange = onTaskCheck
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                if (task.description.isNotEmpty()) {
                    Text(
                        text = task.description,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Status: ${task.status}",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                    Text(
                        text = task.createdAt.substring(0, 10),
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    navController: NavHostController
) {
    val items = listOf("Home", "Calendar", "Document", "Settings")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.DateRange,
        Icons.Default.Info,
        Icons.Default.AccountCircle
    )

    BottomAppBar {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items.take(2).forEachIndexed { index, _ ->
                IconButton(onClick = {
                    onItemSelected(index)
                    if (index == 0) {
                        navController.navigate("smartTasks")
                    }
                }) {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = items[index],
                        tint = if (selectedItem == index) Color.Blue else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.width(56.dp))

            items.drop(2).forEachIndexed { index, _ ->
                IconButton(onClick = {
                    val actualIndex = index + 2
                    onItemSelected(actualIndex)
                    when (actualIndex) {
                        3 -> navController.navigate("user_profile")
                    }
                }) {
                    Icon(
                        imageVector = icons[index + 2],
                        contentDescription = items[index + 2],
                        tint = if (selectedItem == index + 2) Color.Blue else Color.Gray
                    )
                }
            }
        }
    }
}

