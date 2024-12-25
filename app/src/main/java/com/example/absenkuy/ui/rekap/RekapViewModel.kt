package com.example.absenkuy.ui.rekap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absenkuy.R

// ViewModel untuk mengelola data rekap
class RekapViewModel : androidx.lifecycle.ViewModel() {

    // Menyimpan list data rekap yang akan di-observe oleh UI
    val rekapList = androidx.compose.runtime.mutableStateOf<List<Rekap>>(emptyList())

    init {
        // Inisialisasi data rekap saat ViewModel dibuat
        loadRekapData()
    }

    // Simulasi untuk memuat data rekap, ini bisa diganti dengan pemanggilan API atau database
    private fun loadRekapData() {
        val data = listOf(
            Rekap("PTB", "JSI61131", "5 Hadir", "2 Alfa", "0 Izin"),
            Rekap("APSI", "JSI61132", "7 Hadir", "0 Alfa", "0 Izin"),
            Rekap("E-Business", "JSI61128", "7 Hadir", "0 Alfa", "0 Izin"),
            Rekap("Akuisisi Data", "JSI61129", "7 Hadir", "0 Alfa", "0 Izin"),
            Rekap("MHP", "JSI60213", "7 Hadir", "0 Alfa", "0 Izin"),
            Rekap("E-Commerce", "JSI60212", "7 Hadir", "0 Alfa", "0 Izin"),
            Rekap("PSE", "JSI60141", "7 Hadir", "0 Alfa", "0 Izin")
        )
        rekapList.value = data // Menyimpan data ke dalam rekapList
    }
}

// Data class untuk representasi data rekap per mata kuliah
data class Rekap(
    val subject: String, // Nama mata kuliah
    val code: String, // Kode mata kuliah
    val present: String, // Jumlah kehadiran
    val absent: String, // Jumlah ketidakhadiran
    val permission: String // Jumlah izin
)

// UI untuk menampilkan Rekap Kehadiran
@Composable
fun RekapScreen(rekapViewModel: RekapViewModel = viewModel()) {
    // Mengambil data rekap dari ViewModel
    val rekapList by rekapViewModel.rekapList

    Column(
        modifier = Modifier
            .fillMaxSize() // Mengisi seluruh ukuran layar
            .padding(16.dp) // Memberikan padding di seluruh sisi
    ) {
        // Menampilkan logo di bagian atas
        Image(
            painter = painterResource(id = R.drawable.sidelogo), // Mengambil resource gambar
            contentDescription = "Logo", // Deskripsi gambar untuk aksesibilitas
            modifier = Modifier
                .size(150.dp) // Ukuran logo
        )

        // Menampilkan judul layar
        Text(
            text = "Rekap Kehadiran",
            fontSize = 20.sp, // Ukuran font untuk judul
            fontWeight = FontWeight.SemiBold, // Menebalkan font
            modifier = Modifier.padding(vertical = 8.dp) // Memberikan padding atas dan bawah
        )

        // Menambahkan jarak antar elemen
        Spacer(modifier = Modifier.height(8.dp))

        // Baris header tabel untuk kolom: Mata Kuliah, Kode MK, dan Kehadiran
        Row(
            modifier = Modifier
                .fillMaxWidth() // Mengisi lebar layar
                .padding(vertical = 8.dp), // Memberikan jarak vertikal
            horizontalArrangement = Arrangement.SpaceBetween // Menyusun kolom secara merata
        ) {
            Text(text = "Mata Kuliah", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.6f)) // Nama Mata Kuliah
            Text(text = "Kode MK", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.6f)) // Kode Mata Kuliah
            Text(text = "Kehadiran", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f)) // Status Kehadiran
        }

        // Menambahkan jarak antar header dan data
        Spacer(modifier = Modifier.height(8.dp))

        // Menampilkan setiap data rekap dalam bentuk baris
        rekapList.forEach { rekap ->
            RekapRow(rekap) // Menampilkan setiap baris data
        }

        // Menambahkan jarak sebelum tombol unduh
        Spacer(modifier = Modifier.height(16.dp))

        // Tombol untuk mengunduh laporan
        Button(
            onClick = { }, // Aksi saat tombol ditekan
            modifier = Modifier
                .fillMaxWidth() // Membuat tombol mengisi lebar layar
                .padding(vertical = 8.dp)
                .padding(horizontal = 10.dp), // Padding horizontal untuk tombol
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8)) // Warna tombol
        ) {
            // Teks dalam tombol Unduh Laporan
            Text(text = "Unduh Laporan", color = Color.White)
        }
    }
}

// Menampilkan baris data rekap yang terdiri dari Mata Kuliah, Kode MK, dan Status Kehadiran
@Composable
fun RekapRow(rekap: Rekap) {
    Row(
        modifier = Modifier
            .fillMaxWidth() // Mengisi lebar layar
            .padding(vertical = 8.dp), // Memberikan jarak antar baris
        horizontalArrangement = Arrangement.SpaceBetween // Menyusun data secara merata
    ) {
        // Menampilkan nama mata kuliah
        Text(text = rekap.subject, modifier = Modifier.weight(0.6f))
        // Menampilkan kode mata kuliah
        Text(text = rekap.code, modifier = Modifier.weight(0.6f))
        // Menampilkan status kehadiran (Hadir, Alfa, Izin)
        Row(
            modifier = Modifier.weight(1f), // Memberikan ruang untuk status
            horizontalArrangement = Arrangement.SpaceEvenly // Menyusun status secara merata
        ) {
            StatusChip(text = rekap.present, color = Color(0xFF4CAF50)) // Status Hadir (hijau)
            Spacer(modifier = Modifier.width(8.dp)) // Jarak antar status
            StatusChip(text = rekap.absent, color = Color(0xFFF44336)) // Status Alfa (merah)
            Spacer(modifier = Modifier.width(8.dp)) // Jarak antar status
            StatusChip(text = rekap.permission, color = Color(0xFFFFEB3B)) // Status Izin (kuning)
        }
    }
}

// Chip untuk menampilkan status kehadiran (Hadir, Alfa, Izin) dengan warna latar belakang
@Composable
fun StatusChip(text: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color = color, shape = RoundedCornerShape(16.dp)) // Latar belakang dengan warna dan sudut melengkung
            .padding(horizontal = 8.dp, vertical = 4.dp) // Padding dalam chip
    ) {
        // Teks status dengan ukuran font yang lebih kecil
        Text(text = text, color = Color.White, fontSize = 12.sp)
    }
}

// Preview untuk melihat tampilan RekapScreen pada desain
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RekapScreenPreview() {
    RekapScreen() // Menampilkan tampilan dari RekapScreen
}
