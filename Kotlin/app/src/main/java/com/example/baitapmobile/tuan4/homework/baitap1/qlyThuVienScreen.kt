// qlyThuVien.kt
package com.example.baitapmobile.tuan4.homework.baitap1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun BookItem(book: Book, isSelected: Boolean, onCheckedChange: () -> Unit) {
    Card(Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Row(
            Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(book.title, Modifier.weight(1f))
            Checkbox(checked = isSelected, onCheckedChange = { onCheckedChange() })
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
            text = "Hệ thống \n Quản lý Thư viện",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        // Nhập tên sinh viên
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Sinh viên", fontWeight = FontWeight.Medium)

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputName,
                        onValueChange = { inputName = it },
                        placeholder = { Text("Nhập tên sinh viên") },
                        modifier = Modifier.weight(1f)
                    )
                    Button(onClick = {
                        viewModel.updateStudent(inputName)
                    }) {
                        Text("Thay đổi")
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Danh sách sách đã mượn
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Sách đã mượn", fontWeight = FontWeight.Medium)

                val borrowed = viewModel.borrowedBooks()
                if (borrowed.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Bạn chưa mượn quyển sách nào", color = Color.Gray)
                            Text("Nhấn 'Thêm' để bắt đầu hành trình đọc sách!", color = Color.Gray)
                        }
                    }
                } else {
                    LazyColumn {
                        items(borrowed) { book ->
                            BookItem(book, true) {
                                viewModel.toggleBorrow(book)
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Danh sách sách có thể mượn
        if (viewModel.isShowingAvailable()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Chọn sách để mượn", fontWeight = FontWeight.Medium)
                    val available = viewModel.getAvailableBooks()
                    if (available.isEmpty()) {
                        Text("Không còn sách khả dụng.", color = Color.Gray)
                    } else {
                        LazyColumn {
                            items(available) { book ->
                                BookItem(book, false) {
                                    viewModel.toggleBorrow(book)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
        }

        // Nút Thêm / Ẩn
        Button(
            onClick = { viewModel.toggleShowAvailable() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (viewModel.isShowingAvailable()) "Ẩn danh sách" else "Thêm")
        }
    }
}
