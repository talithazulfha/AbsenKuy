package com.example.absenkuy.ui.izin


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.absenkuy.R
import com.example.absenkuy.data.local.UserPreferences


@Composable
fun IzinScreen(navController: NavHostController, viewModel: IzinViewModel) {
    var reason by remember { mutableStateOf("") }
    val context = LocalContext.current
    val pdfUri = remember { mutableStateOf<Uri?>(null) }
    val userPreferences = UserPreferences.getInstance(LocalContext.current)
    val NIM = userPreferences.getUserNIM().collectAsState(initial = "")
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        pdfUri.value = uri
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Formulir permohonan izin",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        // Input Field - Perihal perizinan

        OutlinedTextField(
            value = reason,
            onValueChange = { reason = it },
            label = { Text("Perihal perizinan") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )




        Spacer(modifier = Modifier.height(16.dp))


        // Bukti Pendukung
        Text(
            text = "Bukti Pendukung",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(186.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .background(Color(0xFFF8F8F8)),
            contentAlignment = Alignment.Center
        ) {
            if (pdfUri.value == null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_absensi),
                        contentDescription = "Notification",
                        tint = Color.Black,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Upload your file to start uploading", color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "OR", color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { launcher.launch("application/pdf") }) {
                        Text(text = "Browse files")
                    }
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "File yang dipilih: ${pdfUri.value?.lastPathSegment}",
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        val inputStream = context.contentResolver.openInputStream(pdfUri.value!!)
                        inputStream?.let {
                            try {
                                val fileBytes = it.readBytes()
                                viewModel.uploadFile(reason, fileBytes)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Gagal mengirim file: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }) {
                        Text(text = "Kirim")
                    }
                }
            }




            Spacer(modifier = Modifier.height(8.dp))




            Text(
                text = "Only support .pdf",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )




            Spacer(modifier = Modifier.height(24.dp))


            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        navController.navigate("matkul/${NIM.value}")
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(text = "Batal", color = Color.White)
                }
                Button(onClick = {
                    if (pdfUri.value == null || reason.isBlank()) {
                        Toast.makeText(
                            context,
                            "Isi perihal perizinan dan pilih file PDF!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val inputStream = context.contentResolver.openInputStream(pdfUri.value!!)
                        inputStream?.let {
                            val fileBytes = it.readBytes()
                            viewModel.uploadFile(reason, fileBytes)
                        }
                    }
                },
                    enabled = pdfUri.value != null && reason.isNotBlank()
                    ) {
                    Text(text = "Kirim")
                }
            }
        }
    }
}

