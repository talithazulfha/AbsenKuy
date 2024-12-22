package com.example.absenkuy.ui.mk


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absenkuy.data.response.MatkulResponse
import com.example.absenkuy.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MKViewModel : ViewModel() {
    private val _matkulList = MutableStateFlow<List<String>>(emptyList())
    val matkulList: StateFlow<List<String>> = _matkulList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchMatkul(NIM: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.apiService.getAll(NIM)
                }
                // Extract the list of matkul names from the response
                Log.d("MKViewModel", "Fetching for NIM: $NIM")
                val matkulNames = response.mataKuliah?.filterNotNull() ?: emptyList()
                _matkulList.value = matkulNames
                _error.value = null
            } catch (e: Exception) {
                Log.d("MKViewModel", "Fetching for NIM: $NIM")
                _error.value = "Error fetching courses: ${e.message}"
                Log.e("MKViewModel", "Error: ${e.message}", e)
                _matkulList.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
