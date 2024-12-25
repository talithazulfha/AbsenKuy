package com.example.absenkuy.ui.feedback

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.absenkuy.data.response.FeedbackRequest
import com.example.absenkuy.data.response.MatkulResponse
import com.example.absenkuy.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedbackViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    private val _formState = mutableStateOf(FeedbackFormState())
    val formState: State<FeedbackFormState> = _formState

    private val _matkulList = MutableStateFlow<List<MatkulResponse>>(emptyList())
    val matkulList: StateFlow<List<MatkulResponse>> = _matkulList

    init {
        fetchMataKuliah()
    }

    data class FeedbackFormState(
        val mataKuliah: String = "",
        val kelas: String = "",
        val dosen: String = "",
        val ratingMataKuliah: Int = 0,
        val ratingDosen: Int = 0,
        val komentarMataKuliah: String = "",
        val komentarDosen: String = ""
    )

    fun updateMataKuliah(value: String) {
        _formState.value = _formState.value.copy(mataKuliah = value)
    }

    fun updateKelas(value: String) {
        _formState.value = _formState.value.copy(kelas = value)
    }

    fun updateDosen(value: String) {
        _formState.value = _formState.value.copy(dosen = value)
    }

    fun updateRatingMataKuliah(value: Int) {
        _formState.value = _formState.value.copy(ratingMataKuliah = value)
    }

    fun updateRatingDosen(value: Int) {
        _formState.value = _formState.value.copy(ratingDosen = value)
    }

    fun updateKomentarMataKuliah(value: String) {
        _formState.value = _formState.value.copy(komentarMataKuliah = value)
    }

    fun updateKomentarDosen(value: String) {
        _formState.value = _formState.value.copy(komentarDosen = value)
    }

    fun fetchMataKuliah() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = ApiConfig.apiService.getAllMatkul()
                _matkulList.value = response
                Log.d("FeedbackViewModel", "Matkul loaded: ${response.size} items")
            } catch (e: Exception) {
                Log.e("FeedbackViewModel", "Error loading matkul", e)
                _error.value = "Gagal memuat data mata kuliah: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun submitFeedback(nim: String, kodeJk: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val currentForm = _formState.value

                // Validasi input
                if (nim.isBlank() || kodeJk.isBlank() ||
                    currentForm.mataKuliah.isBlank() ||
                    currentForm.kelas.isBlank() ||
                    currentForm.dosen.isBlank() ||
                    currentForm.ratingMataKuliah == 0 ||
                    currentForm.ratingDosen == 0 ||
                    currentForm.komentarMataKuliah.isBlank() ||
                    currentForm.komentarDosen.isBlank()) {
                    _error.value = "Semua field harus diisi"
                    return@launch
                }

                val response = ApiConfig.apiService.createFeedback(
                    FeedbackRequest(
                        NIM = nim,
                        kode_jk = kodeJk,
                        ratingM = currentForm.ratingMataKuliah,
                        ratingD = currentForm.ratingDosen,
                        komentarM = currentForm.komentarMataKuliah,
                        komentarD = currentForm.komentarDosen,
                        tanggal = System.currentTimeMillis()
                    )
                )
                _isSuccess.value = true
                resetForm()
            } catch (e: Exception) {
                _error.value = e.message ?: "Terjadi kesalahan"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun resetForm() {
        _formState.value = FeedbackFormState()
        _isSuccess.value = false
    }
}