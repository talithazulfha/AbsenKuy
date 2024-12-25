package com.example.absenkuy.ui.izin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absenkuy.data.local.UserPreferences
import com.example.absenkuy.data.request.IzinRequest
import com.example.absenkuy.data.retrofit.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class IzinViewModel (
    private val userPreferences: UserPreferences,
    private val apiService: ApiService
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _uploadResult = mutableStateOf<String?>(null)
    val uploadResult: State<String?> = _uploadResult

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun uploadFile(reason: String, fileBytes: ByteArray) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val nim = userPreferences.getUserNIM().first()
                val response = apiService.uploadIzin(
                    IzinRequest(NIM = nim.toString(), keterangan = reason, bukti = fileBytes)
                )

                _uploadResult.value = response.message
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }

    }
}