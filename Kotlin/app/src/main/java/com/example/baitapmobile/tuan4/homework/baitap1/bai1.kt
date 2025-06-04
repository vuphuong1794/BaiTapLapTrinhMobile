package com.example.baitapmobile.tuan4.homework.baitap1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun baitap4(navHostController: NavHostController) {
    var i by remember { mutableStateOf(0) }
    val user = listUser[i]
    val listBook = listBookAndItsOwner[user] ?: emptyList()
    var showDialog by remember { mutableStateOf(false) }
    val addedBook = remember { mutableStateListOf<String>() }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(vertical = 30.dp, horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Hệ thống\nQuản lý Thư viện",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))

        SinhVienSection(
            user = user,
            onNext = {
                i = (i + 1) % listUser.size
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
        BookSection(user, listBook)
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .width(110.dp)
                .height(55.dp),
            shape = RoundedCornerShape(15.dp),
            onClick = {
                addedBook.clear()
                addedBook.addAll(listBook)
                showDialog = true
            }
        ) {
            Text("Thêm", fontSize = 15.sp)
        }
    }

    if (showDialog) {
        BookDialog(
            user = user,
            addedBook = addedBook,
            onDismiss = { showDialog = false },
            onConfirm = {
                listBookAndItsOwner[user] = addedBook.toList()
            }
        )
    }
}


@Composable
fun BookDialog(
    user: String,
    addedBook: SnapshotStateList<String>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Row {
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    },
                ) {
                    Text("Cập nhật", fontSize = 15.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onDismiss) {
                    Text("Đóng")
                }
            }
        },
        title = {
            Text(
                text = "Thêm sách",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        text = {
            LazyColumn {
                val ownerbooks = listBookAndItsOwner[user] ?: emptyList()
                val bookinlist = listBook.filter {
                    it !in ownerbooks
                }

                item {
                    if (ownerbooks.isEmpty()) {
                        Text("Chưa thêm sách.", fontSize = 15.sp)
                    } else {
                        Text("Sách đã thêm", fontSize = 15.sp)
                        ownerbooks.forEach { book ->
                            BookItemRow(book, addedBook)
                        }
                    }

                    Divider()

                    if (bookinlist.isEmpty()) {
                        Text("Hết sách.", fontSize = 15.sp)
                    } else {
                        Text("Sách còn dư", fontSize = 15.sp)
                        bookinlist.forEach { book ->
                            BookItemRow(book, addedBook)
                        }
                    }
                }
            }
        }
    )
}
@Composable
fun BookItemRow(book: String, addedBook: SnapshotStateList<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (addedBook.contains(book)) {
                    addedBook.remove(book)
                } else {
                    addedBook.add(book)
                }
            }
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = addedBook.contains(book),
            onCheckedChange = {
                if (addedBook.contains(book)) {
                    addedBook.remove(book)
                } else {
                    addedBook.add(book)
                }
            },
            colors = CheckboxDefaults.colors(Color.Red)
        )
        Text(book)
    }
}

@Composable
fun SinhVienSection(user: String, onNext: () -> Unit) {
    Column {
        Text("Sinh viên", fontWeight = FontWeight.Bold)
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(15.dp))
            ) {
                Text(
                    user,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                modifier = Modifier
                    .width(110.dp)
                    .height(55.dp),
                shape = RoundedCornerShape(15.dp),
                onClick = { onNext() }
            ) {
                Text("Thay đổi", fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun BookSection(user: String,listBook: List<String>) {

    Column {
        Text("Danh sách sách của $user", fontWeight = FontWeight.Bold)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            for (book in listBook) {
                item {
                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var i: Boolean by remember { mutableStateOf(true) }
                            Checkbox(
                                checked = i,
                                onCheckedChange = {
                                    i = !i
                                },
                                colors = CheckboxDefaults.colors(Color.Red)
                            )
                            Text(
                                book,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}