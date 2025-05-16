package com.example.baitapmobile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
// THUC HANH 2
fun checkEmail(
    modifier: Modifier
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "Thực hành 02",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            var text by remember { mutableStateOf("") }
            var isEmail by remember { mutableStateOf(false) }
            var isClicked by remember { mutableStateOf(false) }

            fun onClick(text: String){
                if (text.contains("@")){
                    isEmail = true
                }
                else {
                    isEmail = false
                }
            }

            Spacer(Modifier.padding(8.dp))

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Email") }
            )

            if(isClicked){
                if(!text.contains("@")){
                    Text(
                        text = "email khong hop le"
                    )
                }else{
                    Text(
                        text = "email hop le"
                    )
                }
            }

            Button(onClick = { isClicked=true }) {
                Text("Filled")
            }
        }
    }
}

