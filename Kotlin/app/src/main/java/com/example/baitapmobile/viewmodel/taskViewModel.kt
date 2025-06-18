package com.example.baitapmobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baitapmobile.retrofit.RetrofitInstance
import com.example.baitapmobile.responseModel.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(): ViewModel() {
    // UI State
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState

    // Tasks list - đổi từ TaskResponse thành Task vì TaskResponse là wrapper
    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    val taskList: StateFlow<List<Task>> = _taskList

    private val _currentTask = MutableStateFlow<Task?>(null)
    val currentTask: StateFlow<Task?> = _currentTask

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Error state
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                // Nếu getTasks() trả về TaskResponse trực tiếp (không wrap trong Response<>)
                val taskResponse = RetrofitInstance.api.getTasks()

                if (taskResponse.isSuccess) {
                    _taskList.value = taskResponse.data
                    _uiState.value = _uiState.value.copy(
                        tasks = taskResponse.data,
                        isLoading = false,
                        errorMessage = null
                    )
                } else {
                    _errorMessage.value = taskResponse.message
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = taskResponse.message
                    )
                }
            } catch (e: Exception) {
                _errorMessage.value = "Lỗi kết nối: ${e.message}"
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Lỗi kết nối: ${e.message}"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchTaskDetail(taskId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                // Use TaskDetailResponse which returns single Task object
                val taskDetailResponse = RetrofitInstance.api.getTaskDetail(taskId)

                if (taskDetailResponse.isSuccess) {
                    // taskDetailResponse.data is now a single Task object
                    _currentTask.value = taskDetailResponse.data
                } else {
                    _errorMessage.value = taskDetailResponse.message
                }
            } catch (e: Exception) {
                _errorMessage.value = "Lỗi kết nối: ${e.message}"
                e.printStackTrace() // Add this to see the full error in logs
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Helper function to clear error messages
    fun clearError() {
        _errorMessage.value = null
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

// UI State data class
data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedFilter: String = "All"
)