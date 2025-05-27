package com.example.tuan3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextDetailScreen(navController: NavHostController) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Text Detail",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF007AFF)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF007AFF)
                )
            }
        },

    )

    Spacer(modifier = Modifier.padding(16.dp))
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
                    ) {
                        append("B")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFB8860B),
                            fontSize = 24.sp
                        )
                    ) {
                        append("rown")
                    }
                    append("\n")
                    append("fox j u m p s ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    ) {
                        append("over")
                    }
                    append("\n")
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            fontSize = 20.sp
                        )
                    ) {
                        append("the")
                    }
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            fontStyle = FontStyle.Italic,
                            fontFamily = FontFamily.Serif,
                            fontSize = 20.sp
                        )
                    ) {
                        append("lazy")
                    }
                    append(" dog.")
                },
                fontSize = 20.sp,
                lineHeight = 30.sp
            )
        }
    }
}

