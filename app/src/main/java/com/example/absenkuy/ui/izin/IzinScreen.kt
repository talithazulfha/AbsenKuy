package com.example.absenkuy.ui.izin


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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.absenkuy.R




@Composable
fun IzinScreen(navController: NavHostController) {
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
            value = "",
            onValueChange = {},
            label = { Text("Perihal perizinan") },
            modifier = Modifier.fillMaxWidth()
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
                Button(onClick = { /* Browse file logic */ }) {
                    Text(text = "Browse files")
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
                    navController.navigate("matkul")
                },
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(text = "Batal", color = Color.White)
            }
            Button(onClick = { /* Submit logic */ }) {
                Text(text = "Kirim")
            }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IzinScreenPreview() {
    IzinScreen(navController = rememberNavController())
}

