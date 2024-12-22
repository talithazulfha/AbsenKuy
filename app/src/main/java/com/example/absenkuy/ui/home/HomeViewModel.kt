package com.example.absenkuy.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absenkuy.data.local.UserPreferences
import com.example.absenkuy.data.response.HomeResponse
import com.example.absenkuy.data.retrofit.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userPreferences: UserPreferences,
    private val apiService: ApiService
) : ViewModel() {
    private val _userData = mutableStateOf<HomeResponse?>(null)
    val userData: State<HomeResponse?> = _userData

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val nim = userPreferences.getUserNIM().first()
                nim?.let {
                    val response = apiService.getHome(it)
                    _userData.value = response
                }
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.logout()
        }
    }
}