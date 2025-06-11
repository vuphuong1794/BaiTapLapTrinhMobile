package com.example.baitapmobile.tuan5.bai2

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val userName: String?,
    val profilePictureUrl: String?,
    val email: String?,
    val phone: String?
)
