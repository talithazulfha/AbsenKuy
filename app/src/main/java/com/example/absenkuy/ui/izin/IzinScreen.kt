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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight


@OptIn(ExperimentalMaterial3Api::class)
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
            .background(Color.White)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Formulir permohonan izin",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFF1E3266),
                        fontWeight = FontWeight.Medium
                    )
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF1E3266)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Perihal perizinan label
//            Text(
//                text = "Perihal perizinan",
//                style = MaterialTheme.typography.bodyMedium,
//                color = Color(0xFF1E3266),
//                modifier = Modifier.padding(bottom = 8.dp)
//            )

            // Input Field
            OutlinedTextField(
                value = reason,
                onValueChange = { reason = it },
                label = { Text("Perihal Izin") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1E3266),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFF1E3266)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Bukti Pendukung section
            Text(
                text = "Bukti Pendukung",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF1E3266),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Gray.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF8F8F8)),
                contentAlignment = Alignment.Center
            ) {
                if (pdfUri.value == null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_absensi),
                            contentDescription = "Upload",
                            tint = Color(0xFF1E3266),
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Drag your file(s) to start uploading",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "OR",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = { launcher.launch("*/*") },
                            border = BorderStroke(1.dp, Color(0xFF1E3266)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFF1E3266)
                            )
                        ) {
                            Text("Browse files")
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "File yang dipilih: ${pdfUri.value?.lastPathSegment}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF1E3266)
                        )
                    }
                }
            }

            Text(
                text = "Only support .pdf files",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF1E3266)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF1E3266))
                ) {
                    Text(
                        text = "Batal",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Button(
                    onClick = {
                        if (pdfUri.value == null || reason.isBlank()) {
                            Toast.makeText(
                                context,
                                "Isi perihal perizinan dan pilih file!",
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
                    modifier = Modifier.weight(1f),
                    enabled = pdfUri.value != null && reason.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E3266),
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    Text(
                        text = "Kirim",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}
