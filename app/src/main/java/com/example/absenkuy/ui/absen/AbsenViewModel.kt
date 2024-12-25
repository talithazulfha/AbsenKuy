package com.example.absenkuy.ui.absen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absenkuy.data.local.UserPreferences
import com.example.absenkuy.data.request.AbsensiRequest
import com.example.absenkuy.data.retrofit.ApiConfig.apiService
import com.example.absenkuy.data.retrofit.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AbsenViewModel(private val userPreferences: UserPreferences, apiService: ApiService): ViewModel() {

    private val _absenState = MutableStateFlow<Result<String>>(Result.success(""))
    val absenState: StateFlow<Result<String>> = _absenState

    fun createAbsensi(kodeJK: String, NIM: String, lokasi: String, waktu: String, foto: String) {
        viewModelScope.launch {
            try {
                val request = AbsensiRequest(
                    kodeJK = kodeJK,
                    lokasi = lokasi,
                    waktu = waktu,
                    foto = foto
                )
                val response = apiService.absensi(NIM, request)
                if (response != null && response.data != null) {
                    _absenState.value = Result.success("Absensi berhasil dicatat")
                } else {
                    _absenState.value = Result.failure(Exception("Gagal mencatat absensi"))
                }
            } catch (e: Exception) {
                _absenState.value = Result.failure(e)
            }
        }
    }

}
