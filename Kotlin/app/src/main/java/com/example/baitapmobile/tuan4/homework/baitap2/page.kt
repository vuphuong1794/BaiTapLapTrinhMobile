package com.example.baitapmobile.tuan4.homework.baitap2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.baitapmobile.R

@Composable
fun DataFlowNavigation(email2: String?, pass: String?, navHostController: NavHostController){
    var email by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.uth_logo),
            contentDescription = null
        )
        Text(
            "SmartTasks",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            "Forget Password?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            value = email,
            onValueChange = {
                email = it
                isEmailError = !(email.contains("@gmail.com")) // Kiểm tra email hợp lệ
            },
            label = {
                Text("Email")
            },
            isError = isEmailError
        )
        if (isEmailError) {
            Text(
                text = "Email không hợp lệ. Vui lòng nhập đúng định dạng @gmail.com",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                isEmailError = !(email.contains("@gmail.com"))
                if (!isEmailError) {
                    navHostController.navigate("navDataFlow2/$email")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF2196F3))
        ) {
            Text("Next")
        }


        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Email: ${email2 ?: ""}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "Password: ${pass ?: ""}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }


    }
}

@Composable
fun DataFlowNavigation2(email: String, navController: NavHostController) {
    var isOtpError by remember { mutableStateOf(false) }
    var number by remember { mutableStateOf("") }
    var isSubmit by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.uth_logo),
            contentDescription = null
        )
        Text(
            "SmartTasks",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            "Verify Code",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "Enter the code we just sent you on your registered Email",
            fontSize = 15.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            value = number,
            onValueChange = {
                number = it
            },
            label = {
                Text("OTP")
            },
            isError = isOtpError
        )
        if (isOtpError && isSubmit) {
            Text(
                text = "OTP không đúng",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                isSubmit = true
                isOtpError = number != "12345"
                if (!isOtpError) {
                    navController.navigate("navDataFlow3/$email/$number")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF2196F3))
        ) {
            Text("Next")
        }
    }
}


@Composable
fun DataFlowNavigation3(email:String?, otp:String, navController: NavHostController) {
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }
    var isSubmit by remember { mutableStateOf(false) }
    if (otp != "12345") {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("OTP không đúng")
            Button(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("Quay lại")
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.uth_logo),
                contentDescription = null
            )
            Text(
                "SmartTasks",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                "Create new password",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Your new password must be different form \n" +
                        "previously used password",
                fontSize = 15.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                value = password,
                onValueChange = {password=it},
                label = {
                    Text("Password")
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                value = repassword,
                onValueChange = {repassword=it},
                label = {
                    Text("Confirm Password")
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    if (password != repassword) {
                        isSubmit = true
                    } else {
                        navController.navigate("navDataFlow4/$email/$otp/$password")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFF2196F3))
            ) {
                Text("Next")
            }

            if (isSubmit) {
                Text("Mật khẩu nhập lại không khớp", color = Color.Red)
            }
        }
    }
}

@Composable
fun DataFlowNavigation4(email: String?,otp: String?,password:String?,navHostController: NavHostController) {
    var passwordInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var otpInput by remember { mutableStateOf("") }

    println(email + "\n" + otp + "\n" + password)
    var isSubmit by remember { mutableStateOf(false) }
    var isSignIn by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.uth_logo),
            contentDescription = null
        )
        Text(
            "SmartTasks",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            "Confirm",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "We are here to help you!",
            fontSize = 15.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                value = emailInput,
                onValueChange = { emailInput = it },
                label = {
                    Text("Email")
                }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                value = otpInput,
                onValueChange = { otpInput = it },
                label = {
                    Text("Otp")
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                value = passwordInput,
                onValueChange = { passwordInput = it },
                label = {
                    Text("Pass")
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if (emailInput == email && otpInput == otp && passwordInput == password) {
                    isSignIn = true
                    isSubmit = true
                } else {
                    isSignIn = false
                    isSubmit = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFF2196F3))
        ) {
            Text("Submit")
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (isSignIn && isSubmit) {
            Text("Đăng nhập thành công", color = Color.Green, fontSize = 20.sp)
            navHostController.navigate("navDataFlow5/${email}/${password}")
        } else if (isSubmit) {
            Text("Đăng nhập thất bại", color = Color.Red)
        }
    }
}
