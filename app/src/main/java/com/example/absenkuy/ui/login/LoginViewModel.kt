package com.example.absenkuy.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absenkuy.data.local.UserPreferences
import com.example.absenkuy.data.response.LoginResponse
import com.example.absenkuy.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class LoginViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(NIM: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.apiService.login(NIM, password)
                response.accessToken.let { accessToken ->
                    userPreferences.saveIsUserLoggedIn(true)
                    userPreferences.saveToken(accessToken.toString())
                    userPreferences.saveNIM(NIM)
                    userPreferences.saveUser()
                    _loginResult.postValue(Result.success(response))
                }
            } catch (e: Exception) {
                _loginResult.postValue(Result.failure(e))
                Log.e("LoginError", e.message ?: "Unknown error")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
