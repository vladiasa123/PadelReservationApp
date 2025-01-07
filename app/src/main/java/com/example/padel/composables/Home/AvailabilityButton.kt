package com.example.padel.composables.Home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.padel.ViewModels.CalendarViewModel
import com.example.padel.api.RetrofitClient
import com.example.padel.data.ReservationRequest
import com.example.padel.data.ReservationResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import android.util.Base64
import androidx.compose.ui.platform.LocalContext
import com.example.padel.ViewModels.QRViewModel
import java.io.File
import java.io.FileOutputStream


@Composable
fun AvailabilityButton(modifier: Modifier = Modifier) {

    val qrViewModel: QRViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val viewModel: CalendarViewModel = viewModel()
    val context = LocalContext.current


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
                scope.launch {
                    val reservationRequest = ReservationRequest(
                        viewModel.selectedHour ?: "null",
                        viewModel.selectedDay ?: "null",
                        (viewModel.selectedDayId ?: "null").toString()

                    )
                    val response: Response<ReservationResponse> = RetrofitClient.apiService.sendReservation(reservationRequest)

                    if(response.isSuccessful){
                        Log.d("context", "resercation is succesfull")
                        val reservationResponse = response.body()

                        reservationResponse?.let {
                            Log.d("context", "Reservation is successful: ${it.message}")
                          val image =  Base64toBitmap(it.message)
                            val fileName = "reservation.png"
                            val isSaved = saveBitmapToInternalStorage(context , image, fileName)
                            if(isSaved){
                                Log.d("saving", "Image saved succesfully")
                                qrViewModel.updateQrCodeShowing(1)
                            }else{
                                Log.d("saving", "Image was not saved")
                            }
                        } ?: Log.d("context", "Reservation response is null")
                    } else {
                        Log.d("context", "Reservation failed: ${response.message()}")
                    }
                }
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