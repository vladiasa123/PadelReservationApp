package com.example.padel.composables.Home

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

@Composable
fun AvailabilityButton(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val viewModel: CalendarViewModel = viewModel()
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
                        viewModel.selectedDay ?: "null",
                        viewModel.selectedHour ?: "null"
                    )
                    val response: Response<ReservationResponse> = RetrofitClient.apiService.sendReservation(reservationRequest)

                    if(response.isSuccessful){
                        Log.d("context", "resercation is succesfull")
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


@Preview
@Composable
fun previewAvailabilityButton() {
val modifier = Modifier
    val viewModel = CalendarViewModel()
    AvailabilityButton(modifier)
}