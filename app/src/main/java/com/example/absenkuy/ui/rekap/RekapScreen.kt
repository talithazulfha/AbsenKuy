package com.example.absenkuy.ui.rekap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.material3.CardDefaults

// Data class untuk menyimpan informasi kehadiran per mata kuliah
data class Kehadiran(
    val mataKuliah: String, // Nama mata kuliah
    val kodeMK: String, // Kode mata kuliah
    val hadir: Int, // Jumlah kehadiran
    val alfa: Int, // Jumlah alfa (absen tanpa keterangan)
    val izin: Int // Jumlah izin
)

@Composable
fun RekapScreen(navController: NavHostController) {
    // Daftar mata kuliah beserta data kehadiran
    val kehadiranList = listOf(
        Kehadiran("PTB", "JSI61131", 5, 2, 0),
        Kehadiran("APSI", "JSI61132", 7, 0, 0),
        Kehadiran("E-Business", "JSI61128", 7, 0, 0),
        Kehadiran("Akuisisi Data", "JSI61129", 7, 0, 0),
        Kehadiran("MHP", "JSI60213", 7, 0, 0),
        Kehadiran("E-Commerce", "JSI60212", 7, 0, 0),
        Kehadiran("PSE", "JSI60141", 7, 0, 0),
    )

    // Kolom utama untuk menampung seluruh layout pada layar
    Column(
        modifier = Modifier
            .fillMaxSize() // Mengisi seluruh layar secara vertikal dan horizontal
            .padding(16.dp), // Memberikan padding di sekitar konten
        horizontalAlignment = Alignment.CenterHorizontally // Menyusun konten agar terpusat secara horizontal
    ) {
        // Teks judul untuk layar Rekap Kehadiran
        Text(
            text = "Rekap Kehadiran",
            fontSize = 20.sp, // Ukuran font untuk teks judul
            fontWeight = FontWeight.Bold, // Menebalkan teks judul
            modifier = Modifier.padding(bottom = 16.dp) // Menambahkan padding bawah untuk memberi jarak
        )

        // Kolom untuk menyusun tabel kehadiran
        Column(
            modifier = Modifier.fillMaxWidth() // Membuat kolom mengisi lebar layar
        ) {
            // Baris header tabel yang menampilkan nama kolom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), // Memberikan padding di sekitar header
                horizontalArrangement = Arrangement.SpaceBetween // Menyusun elemen header agar tersebar merata
            ) {
                Text("Mata Kuliah", fontWeight = FontWeight.Bold) // Judul kolom untuk mata kuliah
                Text("Kode MK", fontWeight = FontWeight.Bold) // Judul kolom untuk kode mata kuliah
                Text("Kehadiran", fontWeight = FontWeight.Bold) // Judul kolom untuk status kehadiran
            }

            // Memberikan jarak antar header dan data
            Spacer(modifier = Modifier.height(8.dp))

            // Loop untuk menampilkan setiap data kehadiran dari kehadiranList
            kehadiranList.forEach { kehadiran ->
                // Card untuk setiap baris yang menampilkan data mata kuliah dan status kehadiran
                Card(
                    modifier = Modifier
                        .fillMaxWidth() // Mengisi seluruh lebar kartu
                        .padding(vertical = 4.dp), // Memberikan jarak antar kartu
                    shape = RoundedCornerShape(8.dp), // Membuat sudut kartu melengkung
                    elevation = CardDefaults.cardElevation(4.dp) // Memberikan efek bayangan pada kartu
                ) {
                    // Baris untuk menampilkan data mata kuliah, kode MK, dan status kehadiran
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), // Memberikan padding di dalam kartu
                        horizontalArrangement = Arrangement.SpaceBetween // Menyusun elemen secara merata
                    ) {
                        Text(kehadiran.mataKuliah) // Menampilkan nama mata kuliah
                        Text(kehadiran.kodeMK) // Menampilkan kode mata kuliah

                        // Baris untuk menampilkan status kehadiran (Hadir, Alfa, Izin)
                        Row {
                            // Menampilkan status 'Hadir' dengan warna hijau
                            KehadiranStatus(
                                text = "${kehadiran.hadir} Hadir",
                                backgroundColor = Color(0xFF4CAF50) // Warna hijau untuk hadir
                            )
                            Spacer(modifier = Modifier.width(4.dp)) // Memberikan jarak antar status
                            // Menampilkan status 'Alfa' dengan warna merah
                            KehadiranStatus(
                                text = "${kehadiran.alfa} Alfa",
                                backgroundColor = Color(0xFFFF5722) // Warna merah untuk alfa
                            )
                            Spacer(modifier = Modifier.width(4.dp)) // Memberikan jarak antar status
                            // Menampilkan status 'Izin' dengan warna kuning
                            KehadiranStatus(
                                text = "${kehadiran.izin} Izin",
                                backgroundColor = Color(0xFFFFEB3B) // Warna kuning untuk izin
                            )
                        }
                    }
                }
            }
        }

        // Memberikan jarak sebelum tombol unduh laporan
        Spacer(modifier = Modifier.height(16.dp))

        // Tombol untuk mengunduh laporan
        Button(
            onClick = { /* Handle unduhan laporan */ }, // Menangani aksi tombol, seperti unduh laporan
            modifier = Modifier.fillMaxWidth() // Membuat tombol mengisi lebar layar
        ) {
            Text(text = "Unduh Laporan") // Teks di dalam tombol
        }
    }
}

// Fungsi composable untuk menampilkan status kehadiran (Hadir, Alfa, Izin) dengan warna latar belakang
@Composable
fun KehadiranStatus(text: String, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(8.dp)) // Latar belakang dengan warna dan sudut melengkung
            .padding(horizontal = 8.dp, vertical = 4.dp) // Padding di dalam status box
    ) {
        // Menampilkan teks status (misalnya, "5 Hadir")
        Text(
            text = text,
            fontSize = 12.sp, // Ukuran font untuk teks status
            color = Color.White // Warna teks putih agar kontras dengan latar belakang
        )
    }
}
