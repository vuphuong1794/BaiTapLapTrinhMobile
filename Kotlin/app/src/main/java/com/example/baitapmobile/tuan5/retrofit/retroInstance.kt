package com.example.baitapmobile.tuan5.retrofit

import com.example.baitapmobile.tuan5.responseModel.productResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("v2/product")
    suspend fun getProduct(): productResponse
}

object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://mock.apidog.com/m1/890655-872447-default/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
