package com.example.baitapmobile.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baitapmobile.responseModel.productResponse
import com.example.baitapmobile.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class productViewModel(): ViewModel() {
    var product by mutableStateOf<productResponse?>(null)
    var isLoading by mutableStateOf(true)

    fun fetchProductData() {
        viewModelScope.launch {
            try {
                product = RetrofitInstance.api.getProduct()
            } catch(e: Exception) {
                Log.e("productViewModel", "Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}