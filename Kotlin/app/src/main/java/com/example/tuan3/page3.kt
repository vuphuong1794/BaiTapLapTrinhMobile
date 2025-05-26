package com.example.tuan3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextDetailScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)

        ) {
            Text(
                text = buildAnnotatedString {
                    append("The quick ")
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFB8860B),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) { // Màu nâu và kích thước lớn hơn cho "Brown"
                        append("B")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFB8860B),
                            fontSize = 24.sp
                        )
                    ) { // Màu nâu cho phần còn lại của "Brown"
                        append("rown")
                    }
                    append("\n") // Xuống dòng
                    append("fox j u m p s ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    ) { // In đậm "over"
                        append("over")
                    }
                    append("\n") // Xuống dòng
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            fontSize = 20.sp
                        )
                    ) { // Gạch chân "the"
                        append("the")
                    }
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            fontStyle = FontStyle.Italic,
                            fontFamily = FontFamily.Serif,
                            fontSize = 20.sp
                        )
                    ) { // In nghiêng và font serif cho "lazy"
                        append("lazy")
                    }
                    append(" dog.")
                },
                fontSize = 20.sp, // Kích thước chữ mặc định
                lineHeight = 30.sp // Điều chỉnh khoảng cách giữa các dòng nếu cần
            )
        }
    }
}

