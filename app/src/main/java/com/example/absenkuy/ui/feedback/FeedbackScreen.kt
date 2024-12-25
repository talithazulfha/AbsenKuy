package com.example.absenkuy.ui.feedback

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.absenkuy.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(
    navController: NavController,
    viewModel: FeedbackViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // Collect ViewModel states
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()
    val formState by viewModel.formState
    val matkulList by viewModel.matkulList.collectAsState()

    // State for dropdown
    var expanded by remember { mutableStateOf(false) }

    // Success handling
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            navController.popBackStack()
        }
    }

    // Debug logging
    LaunchedEffect(matkulList) {
        Log.d("FeedbackScreen", "Matkul list updated: ${matkulList.size} items")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Formulir Feedback Perkuliahan",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Mata Kuliah Dropdown
        Text(
            text = "Nama Mata Kuliah",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = formState.mataKuliah,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                enabled = !isLoading,
                placeholder = { Text("Pilih mata kuliah") }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (matkulList.isEmpty() && !isLoading) {
                    DropdownMenuItem(
                        text = { Text("Tidak ada data mata kuliah") },
                        onClick = { }
                    )
                } else {
                    matkulList.forEach { matkul ->
                        DropdownMenuItem(
                            text = { Text("${matkul.namaMatkul} (${matkul.id})") },
                            onClick = {
                                viewModel.updateMataKuliah(matkul.namaMatkul)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Kelas
        Text(
            text = "Kelas",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = formState.kelas,
            onValueChange = { viewModel.updateKelas(it) },
            placeholder = { Text("Masukkan kelas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dosen
        Text(
            text = "Nama Dosen",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = formState.dosen,
            onValueChange = { viewModel.updateDosen(it) },
            placeholder = { Text("Masukkan nama dosen") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Rating Mata Kuliah
        Text(
            text = "Rating Mata Kuliah",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            (1..5).forEach { rating ->
                IconButton(
                    onClick = { viewModel.updateRatingMataKuliah(rating) },
                    enabled = !isLoading
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (rating <= formState.ratingMataKuliah) {
                                R.drawable.ic_star_filled
                            } else {
                                R.drawable.ic_star_outline
                            }
                        ),
                        contentDescription = "Rating $rating",
                        tint = if (rating <= formState.ratingMataKuliah) {
                            Color(0xFFFFD700)
                        } else {
                            Color.Gray
                        },
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Komentar Mata Kuliah
        Text(
            text = "Komentar Mata Kuliah",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = formState.komentarMataKuliah,
            onValueChange = { viewModel.updateKomentarMataKuliah(it) },
            placeholder = { Text("Masukkan komentar tentang mata kuliah") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Rating Dosen
        Text(
            text = "Rating Dosen",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            (1..5).forEach { rating ->
                IconButton(
                    onClick = { viewModel.updateRatingDosen(rating) },
                    enabled = !isLoading
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (rating <= formState.ratingDosen) {
                                R.drawable.ic_star_filled
                            } else {
                                R.drawable.ic_star_outline
                            }
                        ),
                        contentDescription = "Rating $rating",
                        tint = if (rating <= formState.ratingDosen) {
                            Color(0xFFFFD700)
                        } else {
                            Color.Gray
                        },
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Komentar Dosen
        Text(
            text = "Komentar Dosen",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = formState.komentarDosen,
            onValueChange = { viewModel.updateKomentarDosen(it) },
            placeholder = { Text("Masukkan komentar tentang dosen") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Error Message
        error?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        // Submit Button
        Button(
            onClick = {
                scope.launch {
                    viewModel.submitFeedback(
                        nim = "YOUR_NIM", // Get from UserPreferences
                        kodeJk = "YOUR_KODE_JK" // Get from parameters
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = !isLoading &&
                    formState.mataKuliah.isNotBlank() &&
                    formState.kelas.isNotBlank() &&
                    formState.dosen.isNotBlank() &&
                    formState.ratingMataKuliah > 0 &&
                    formState.ratingDosen > 0 &&
                    formState.komentarMataKuliah.isNotBlank() &&
                    formState.komentarDosen.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1565C0)
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Submit Feedback")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedbackScreenPreview() {
    MaterialTheme {
        FeedbackScreen(navController = rememberNavController())
    }
}