package com.example.absenkuy.ui.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.absenkuy.R

@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel) {
    val userData = homeViewModel.userData.value
    val isLoading = homeViewModel.isLoading.value
    val error = homeViewModel.error.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E3266),
                        Color(0xFF1E3266)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection()

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.White
                )
            }
            error != null -> {
                Text(
                    text = "Error: $error",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {
                ProfileCard(
                    nama = userData?.nama ?: "",
                    departemen = userData?.departemen ?: ""
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        MenuGrid(navController)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                homeViewModel.logout()
                navController.navigate("login")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Keluar")
        }
    }
}


@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "AbsenKUY",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_notification),
            contentDescription = "Notification",
            tint = Color.White
        )
    }
}


@Composable
fun ProfileCard(nama: String, departemen: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.LightGray, CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_account),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = departemen,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.ic_edit_password),
                contentDescription = "Edit",
                tint = Color.Gray
            )
        }
    }
}


@Composable
fun MenuGrid(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            MenuCard(
                icon = R.drawable.ic_absensi,
                label = "Absensi",
                onClick = {
                    navController.navigate("matkul/{$}")
                }
            )
            MenuCard(
                icon = R.drawable.ic_rekap_kehadiran,
                label = "Rekap Kehadiran",
                onClick = {
                    navController.navigate("rekap_kehadiran")
                }
            )
        }


        Spacer(modifier = Modifier.height(16.dp))


        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            MenuCard(
                icon = R.drawable.ic_feedback,
                label = "Feedback Perkuliahan",
                onClick = {
                    navController.navigate("feedback_perkuliahan")
                }
            )
            MenuCard(
                icon = R.drawable.ic_edit_password,
                label = "Edit Password",
                onClick = {
                    navController.navigate("edit_password")
                }
            )
        }
    }
}


@Composable
fun MenuCard(icon: Int, label: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = Color.Black,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}
