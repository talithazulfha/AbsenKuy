package com.example.absenkuy.ui.rekap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.absenkuy.R

@Composable
fun RekapScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.sidelogo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(150.dp)
        )

        Text(
            text = "Rekap Kehadiran",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Mata Kuliah",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(0.6f)
            )
            Text(text = "Kode MK",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(0.6f)
            )
            Text(text = "Kehadiran",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val rekapList = listOf(
            Rekap("PTB", "JSI61131", "5 Hadir", "2 Alfa", "0 Izin"),
            Rekap("APSI", "JSI61132", "7 Hadir", "0 Alfa", "0 Izin"),
            Rekap("E-Business", "JSI61128", "7 Hadir", "0 Alfa", "0 Izin"),
            Rekap("Akuisisi Data", "JSI61129", "7 Hadir", "0 Alfa", "0 Izin"),
            Rekap("MHP", "JSI60213", "7 Hadir", "0 Alfa", "0 Izin"),
            Rekap("E-Commerce", "JSI60212", "7 Hadir", "0 Alfa", "0 Izin"),
            Rekap("PSE", "JSI60141", "7 Hadir", "0 Alfa", "0 Izin")
        )

        rekapList.forEach { rekap ->
            RekapRow(rekap)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Download Button
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .padding(horizontal = 10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8))
        ) {
            Text(text = "Unduh Laporan", color = Color.White)
        }
    }
}

@Composable
fun RekapRow(rekap: Rekap) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = rekap.subject, modifier = Modifier.weight(0.6f))
        Text(text = rekap.code, modifier = Modifier.weight(0.6f))
        Row (
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            StatusChip(text = rekap.present, color = Color(0xFF4CAF50))
            Spacer(modifier = Modifier.width(8.dp))
            StatusChip(text = rekap.absent, color = Color(0xFFF44336))
            Spacer(modifier = Modifier.width(8.dp))
            StatusChip(text = rekap.permission, color = Color(0xFFFFEB3B))
        }
    }
}

@Composable
fun StatusChip(text: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color = color, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = 12.sp)
    }
}

data class Rekap(
    val subject: String,
    val code: String,
    val present: String,
    val absent: String,
    val permission: String
)


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RekapScreenPreview() {
    RekapScreen()
}