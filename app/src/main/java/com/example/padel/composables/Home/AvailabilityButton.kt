package com.example.padel.composables.Home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.padel.ViewModels.CalendarViewModel
import java.io.File
import java.io.FileOutputStream


@Composable
fun AvailabilityButton(modifier: Modifier = Modifier) {



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 0.dp)
    ) {
        Text(
            "Date is available",
            modifier = Modifier.align(CenterHorizontally),
            style = MaterialTheme.typography.headlineSmall
        )
        Button(
            onClick = {
            },
            modifier = Modifier
                .align(CenterHorizontally)
                .width(250.dp)
                .padding(top = 10.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)

        ) {
            Icon(
                Icons.Filled.Check,
                contentDescription = "Date Available",
                modifier = Modifier.padding(end = 5.dp)
            )
            Text(
                text = "Confirm reservation",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


fun Base64toBitmap(base64String: String): Bitmap {
    val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}

@SuppressLint("SuspiciousIndentation")
fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap, fileName: String): Boolean {
    val directory = context.filesDir
    val file = File(directory, fileName)

    return try{
        val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()
        true
    } catch (e: Exception){
        e.printStackTrace()
        false
    }
}





@Preview
@Composable
fun previewAvailabilityButton() {
val modifier = Modifier
    val viewModel = CalendarViewModel()
    AvailabilityButton(modifier)
}