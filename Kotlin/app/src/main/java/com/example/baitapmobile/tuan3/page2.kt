package com.example.baitapmobile.tuan3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun page2(modifier: Modifier, navController: NavHostController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "UI Components List",
            color = Color.Blue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Display", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        ComponentItem(title = "Text", description = "Displays text", onClick = {navController.navigate("page3")})
        ComponentItem(title = "Image", description = "Displays an image", onClick = {navController.navigate("page4")})

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Input", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        ComponentItem(title = "TextField", description = "Input field for text",onClick = {navController.navigate("page5")} )
        ComponentItem(title = "PasswordField", description = "Input field for passwords",onClick = {})

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Layout", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        ComponentItem(title = "Column", description = "Arranges elements vertically",onClick = {navController.navigate("page7")})
        ComponentItem(title = "Row", description = "Arranges elements horizontally",onClick = {navController.navigate("page6")})
        ComponentItem(title = "lazyCol", description = "lazy column", onClick = {navController.navigate("lazyCol")})

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Page 1")
        }
    }
}

@Composable
fun ComponentItem(title: String, description: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(color = Color(0xFFBBDEFB), shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Text(text = title, fontWeight = FontWeight.Bold)
        Text(text = description)
    }
}


