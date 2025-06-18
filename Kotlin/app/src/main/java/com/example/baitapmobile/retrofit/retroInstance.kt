package com.example.baitapmobile.retrofit

import com.example.baitapmobile.responseModel.Task
import com.example.baitapmobile.responseModel.TaskDetailResponse
import com.example.baitapmobile.responseModel.TaskResponse
import com.example.baitapmobile.responseModel.productResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("v2/product")
    suspend fun getProduct(): productResponse

    @GET("tasks")
    suspend fun getTasks(): TaskResponse

    @GET("task/{id}")
    suspend fun getTaskDetail(@Path("id") id: Int): TaskDetailResponse
}

object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://amock.io/api/researchUTH/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
