package com.example.baitapmobile.tuan4.homework.baitap1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
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
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6)),
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
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFFB71C1C))
            )
        }
    }
}

@Composable
fun BookItemWithDelete(book: Book, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(book.title, fontWeight = FontWeight.Medium)
                Text(book.author, fontSize = 14.sp, color = Color.Gray)
                Text("ID: ${book.id}", fontSize = 12.sp, color = Color.Gray)
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Xóa sách",
                    tint = Color(0xFFB71C1C)
                )
            }
        }
    }
}

@Composable
fun ManagementTab(viewModel: LibraryViewModel) {
    var inputName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6)),
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
                            focusedBorderColor = Color(0xFF7E57C2),
                            unfocusedBorderColor = Color(0xFF7E57C2)
                        )
                    )
                    Button(
                        onClick = {
                            viewModel.updateStudent(inputName)
                            inputName = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Thay đổi", color = Color.White)
                    }
                }

                // Hiển thị sinh viên hiện tại
                val currentStudent by viewModel.currentStudent
                currentStudent?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Sinh viên hiện tại: ${it.name}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF7E57C2)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
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

        Button(
            onClick = { viewModel.toggleShowAvailable() },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.currentStudent.value != null,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (viewModel.isShowingAvailable()) "Xem sách đã mượn" else "Thêm",
                color = Color.White
            )
        }
    }
}

@Composable
fun BookListTab(viewModel: LibraryViewModel) {
    var bookTitle by remember { mutableStateOf("") }
    var bookAuthor by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6)),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Tạo sách mới", fontWeight = FontWeight.Medium)

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = bookTitle,
                    onValueChange = { bookTitle = it },
                    placeholder = { Text("Nhập tiêu đề sách") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF7E57C2),
                        unfocusedBorderColor = Color(0xFF7E57C2)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = bookAuthor,
                    onValueChange = { bookAuthor = it },
                    placeholder = { Text("Nhập tên tác giả") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF7E57C2),
                        unfocusedBorderColor = Color(0xFF7E57C2)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (bookTitle.isNotBlank() && bookAuthor.isNotBlank()) {
                            val success = viewModel.addBook(bookTitle, bookAuthor)
                            if (success) {
                                showMessage = "Thêm sách thành công!"
                                bookTitle = ""
                                bookAuthor = ""
                            } else {
                                showMessage = "Thêm sách thất bại!"
                            }
                        } else {
                            showMessage = "Vui lòng nhập đầy đủ thông tin!"
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Thêm sách", color = Color.White)
                }

                // Hiển thị thông báo
                if (showMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = showMessage,
                        color = if (showMessage.contains("thành công")) Color(0xFF4CAF50) else Color(0xFFB71C1C),
                        fontSize = 14.sp
                    )
                    LaunchedEffect(showMessage) {
                        kotlinx.coroutines.delay(3000)
                        showMessage = ""
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Danh sách tất cả sách", fontWeight = FontWeight.Medium)

                Spacer(modifier = Modifier.height(8.dp))

                val allBooks by remember { derivedStateOf { viewModel.getAllBooks() } }

                if (allBooks.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Chưa có sách nào trong thư viện",
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn {
                        items(allBooks) { book ->
                            BookItemWithDelete(
                                book = book,
                                onDelete = {
                                    val success = viewModel.removeBook(book.id)
                                    showMessage = if (success) {
                                        "Xóa sách thành công!"
                                    } else {
                                        "Không thể xóa sách này (có thể đang được mượn)!"
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudentTab(viewModel: LibraryViewModel) {
    var studentName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6)),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Tạo sinh viên mới", fontWeight = FontWeight.Medium)

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = studentName,
                    onValueChange = { studentName = it },
                    placeholder = { Text("Nhập tên sinh viên") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF7E57C2),
                        unfocusedBorderColor = Color(0xFF7E57C2)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (studentName.isNotBlank()) {
                            viewModel.updateStudent(studentName)
                            studentName = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Thêm sinh viên", color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị thông tin sinh viên hiện tại
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Thông tin sinh viên hiện tại", fontWeight = FontWeight.Medium)

                Spacer(modifier = Modifier.height(8.dp))

                val currentStudent by viewModel.currentStudent
//                if (currentStudent != null) {
//                    Text("Tên: ${currentStudent!!.name}", fontSize = 16.sp)
//
//                    val borrowedBooks by remember { derivedStateOf { viewModel.getBorrowedBooksBy(currentStudent!!) } }
//                    Text("Số sách đã mượn: ${borrowedBooks.size}", fontSize = 14.sp, color = Color.Gray)
//
//                    if (borrowedBooks.isNotEmpty()) {
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text("Sách đã mượn:", fontSize = 14.sp, fontWeight = FontWeight.Medium)
//                        borrowedBooks.forEach { book ->
//                            Text("• ${book.title}", fontSize = 12.sp, color = Color.Gray)
//                        }
//                    }
//                } else {
//                    Text("Chưa có sinh viên nào được chọn", color = Color.Gray)
//                }
            }
        }
    }
}

@Composable
fun qlyThuVien(navController: NavHostController, viewModel: LibraryViewModel = viewModel()) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = Color.Black
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Quản lý") },
                    text = { Text("Quản lý") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.List, contentDescription = "DS Sách") },
                    text = { Text("DS Sách") }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Sinh viên") },
                    text = { Text("Sinh viên") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Hệ thống\nQuản lý Thư viện",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            when (selectedTab) {
                0 -> ManagementTab(viewModel)
                1 -> BookListTab(viewModel)
                2 -> StudentTab(viewModel)
            }
        }
    }
}