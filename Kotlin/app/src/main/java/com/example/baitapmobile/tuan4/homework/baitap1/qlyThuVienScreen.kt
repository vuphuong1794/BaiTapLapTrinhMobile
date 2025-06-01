package com.example.baitapmobile.tuan4.homework.baitap1

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun BookItem(book: Book, isSelected: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6)), // Light purple background
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(book.title, fontWeight = FontWeight.Medium)
                Text(book.author, fontSize = 14.sp, color = Color.Gray)
            }
            Checkbox(
                checked = isSelected,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFFB71C1C)) // Red checkbox
            )
        }
    }
}

@Composable
fun qlyThuVien(navController: NavHostController, viewModel: LibraryViewModel = viewModel()) {
    var inputName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Hệ thống\nQuản lý Thư viện",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Nhập tên sinh viên
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6)), // Light purple background
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Sinh viên", fontWeight = FontWeight.Medium)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputName,
                        onValueChange = { inputName = it },
                        placeholder = { Text("Nhập tên sinh viên") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF7E57C2), // Purple border
                            unfocusedBorderColor = Color(0xFF7E57C2)
                        )
                    )
                    Button(
                        onClick = {
                            viewModel.updateStudent(inputName)
                            inputName = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2)), // Purple button
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Thay đổi", color = Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Danh sách sách
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)), // Light gray background
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                val isShowingAvailable = viewModel.isShowingAvailable()
                Text(
                    text = if (isShowingAvailable) "Sách khả dụng để mượn" else "Sách đã mượn",
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                val currentStudent by viewModel.currentStudent
                val displayBooks by remember { derivedStateOf { viewModel.getDisplayBooks() } }

                if (displayBooks.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when {
                                currentStudent == null -> "Vui lòng nhập tên sinh viên."
                                isShowingAvailable -> "Không có sách nào khả dụng để mượn."
                                else -> "Bạn chưa mượn quyển sách nào \n Nhấn \"Thêm\" để bắt đầu hành trình đọc sách! "
                            },
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn {
                        items(displayBooks) { book ->
                            val isBorrowed by remember(book.id) { derivedStateOf { viewModel.isBookBorrowed(book) } }
                            BookItem(
                                book = book,
                                isSelected = isBorrowed,
                                onCheckedChange = { _ ->
                                    viewModel.toggleBorrow(book)
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nút chuyển đổi chế độ
        Button(
            onClick = { viewModel.toggleShowAvailable() },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.currentStudent.value != null,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2)), // Purple button
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (viewModel.isShowingAvailable())
                    "Xem sách đã mượn"
                else
                    "Thêm",
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "home",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Quản lý",
                    fontSize = 12.sp
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "list",
                        tint = Color.White
                    )
                }
                Text(
                    text = "DS Sách",
                    fontSize = 12.sp
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "user",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Sinh viên",
                    fontSize = 12.sp
                )
            }
        }
    }
}