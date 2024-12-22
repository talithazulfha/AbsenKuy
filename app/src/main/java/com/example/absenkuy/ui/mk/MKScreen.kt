package com.example.absenkuy.ui.mk

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.absenkuy.data.local.UserPreferences

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MKScreen(
    navController: NavHostController,
    viewModel: MKViewModel = viewModel(),
    NIM: String
) {
    val matkulList by viewModel.matkulList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val userPreferences = UserPreferences.getInstance(LocalContext.current)
    val userNIM = userPreferences.getUserNIM().collectAsState(initial = "")

    // Only fetch when we have a valid NIM
    LaunchedEffect(userNIM.value) {
        if (userNIM.value?.isNotEmpty() == true) {
            Log.d("MKScreen", "Fetching matkul for NIM: ${userNIM.value}")
            viewModel.fetchMatkul(userNIM.value.toString())
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mata Kuliah",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1565C0),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when {
                userNIM.value?.isEmpty() != false -> {
                    Text(
                        text = "Loading user data...",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Text(
                        text = error ?: "Unknown error",
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                matkulList.isEmpty() -> {
                    Text(
                        text = "No courses available",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                else -> {
                    MKList(
                        mkList = matkulList,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun MKList(
    mkList: List<String>, // Changed from List<MatkulResponse>
    navController: NavHostController
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(mkList) { mkName ->
            MKItem(
                mkName = mkName,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MKItem(
    mkName: String,
    navController: NavHostController
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(4.dp), // Tambahkan padding agar lebih rapih
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = mkName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Expand course options"
                    )
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { navController.navigate("presensi") }, // Navigasi ke halaman presensi
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Ambil Presensi")
                    }
                    Button(
                        onClick = { navController.navigate("izin") }, // Navigasi ke halaman izin
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Permohonan Izin")
                    }
                }
            }
        }
    }
}
