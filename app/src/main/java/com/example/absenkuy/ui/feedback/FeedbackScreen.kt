package com.example.absenkuy.ui.feedback

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun FeedbackScreen() {
    var courseName = remember { mutableStateOf(TextFieldValue()) }
    var courseClass = remember { mutableStateOf(TextFieldValue()) }
    var lecturerName = remember { mutableStateOf(TextFieldValue()) }
    var reportSuggesstion = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Formulir Feedback Perkuliahan",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nama Mata Kuliah",
            fontWeight = FontWeight.SemiBold
        )
        TextField(
            value = "",
            onValueChange = {  },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Kelas mata Kuliah",
            fontWeight = FontWeight.SemiBold)
        TextField(
            value = "",
            onValueChange = {  },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Dosen yang bersangkutan",
            fontWeight = FontWeight.SemiBold)
        TextField(
            value = "",
            onValueChange = {  },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text("Laporan dan Saran",
            fontWeight = FontWeight.SemiBold)
        TextField(
            value = "",
            onValueChange = {  },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
        ) {
            Text(text = "Submit", color = Color.White)
        }

    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FeedbackScreenPreview() {
    FeedbackScreen()
}