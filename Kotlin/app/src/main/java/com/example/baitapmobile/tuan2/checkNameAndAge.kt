package com.example.baitapmobile.tuan2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun UserInfoForm() {
    var userName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Tiêu đề
        Text(
            text = "THỰC HÀNH 01",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
        )

        // Khung nhập thông tin
        Column(
            modifier = Modifier
                .background(Color.LightGray, shape = MaterialTheme.shapes.medium)
                .padding(16.dp)
                .width(300.dp)
        ) {
            // Họ và tên
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Họ và tên",
                    modifier = Modifier.width(80.dp)
                )
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    modifier = Modifier.weight(1f)
                )
            }

            // Tuổi
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Tuổi",
                    modifier = Modifier.width(80.dp)
                )
                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Nút kiểm tra
        Button(
            onClick = {
                result = if (userName.isBlank() || age.isBlank()) {
                    "Vui lòng nhập đủ thông tin"
                } else {
                    val ageInt = age.toIntOrNull()
                    if (ageInt == null || ageInt < 0) {
                        "Tuổi không hợp lệ"
                    } else {
                        val type = when {
                            ageInt > 65 -> "Người già"
                            ageInt in 6..65 -> "Người lớn"
                            ageInt in 2..5 -> "Trẻ em"
                            else -> "Em bé"
                        }
                        type
                    }
                }
            },
            modifier = Modifier.width(120.dp)
        ) {
            Text("Kiểm tra")
        }

        if (result.isNotBlank()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = result)
        }
    }
}
