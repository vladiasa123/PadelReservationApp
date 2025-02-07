import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.padel.ViewModels.CalendarViewModel
import com.example.padel.ViewModels.JwtTokenViewModel
import com.example.padel.ViewModels.QRViewModel
import com.example.padel.api.RetrofitClient
import com.example.padel.composables.Home.Base64toBitmap
import com.example.padel.composables.Home.saveBitmapToInternalStorage
import com.example.padel.data.ReservationRequest
import com.example.padel.data.ReservationResponse
import kotlinx.coroutines.launch
import retrofit2.Response


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpwardPopUpCard(
    modifier: Modifier = Modifier, size: Dp
) {  var calendarViewModel: CalendarViewModel = viewModel()
    val qrViewModel: QRViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val jwtViewModel: JwtTokenViewModel = viewModel()

    Box(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .background(Color.White)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(size)
                .background(Color(0xFF262e3a))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(Color(0xFF2f3947)),
                    shape = RectangleShape
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = {
                             calendarViewModel.buttonPressedState = false
                        }, modifier = Modifier.align(TopEnd)) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Closing Button"
                            )
                        }
                    }
                    Text(
                        "Court 1", color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    textAlign = TextAlign.Center
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .height(50.dp)
                        .width(200.dp)
                        .padding(end = 10.dp),
                    colors = CardDefaults.elevatedCardColors(Color(0xFF2f3947))

                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Center
                    ) {
                        Text(
                            "January 15",color = Color.White,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Light
                        )
                    }
                }

                ElevatedCard(
                    modifier = Modifier
                        .height(50.dp)
                        .width(150.dp),
                    colors = CardDefaults.elevatedCardColors(Color(0xFF2f3947))
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Center
                    ) {
                        Text(
                            "${calendarViewModel.selectedHour}",color = Color.White,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                var clicked by remember {
                    mutableStateOf(false)
                }
                ElevatedButton(
                    onClick = {
                        clicked = true
                        scope.launch {
                            val sharedPreferences =
                                context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            val token = sharedPreferences.getString("auth_token", null)
                            if (token != null) {
                                jwtViewModel.decodeToken(token)
                            }
                            val userId = jwtViewModel.usersId.value
                            Log.d("User ID", "Decoded User ID: $userId")

                            calendarViewModel.reservedHour = true
                            val reservationRequest = ReservationRequest(
                                calendarViewModel.selectedHour ?: "null",
                                calendarViewModel.selectedDay ?: "null",
                                (calendarViewModel.selectedDayId ?: "null").toString(),
                                userId = userId.toString()

                            )
                            val response: Response<ReservationResponse> =
                                RetrofitClient.apiService.sendReservation(reservationRequest)

                            if (response.isSuccessful) {
                                val reservationResponse = response.body()

                                reservationResponse?.let {
                                    val image = Base64toBitmap(it.message)
                                    val fileName = "reservation${calendarViewModel.selectedHour}.png"
                                    val isSaved = saveBitmapToInternalStorage(context, image, fileName)
                                    if (isSaved) {
                                        qrViewModel.updateQrCodeShowing(1)
                                    } else {
                                        Log.d("saving", "Image was not saved")
                                    }
                                } ?: Log.d("context", "Reservation response is null")
                            } else {
                                Log.d("context", "Reservation failed: ${response.message()}")
                            }
                        }
                    } ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                    colors = ButtonDefaults.elevatedButtonColors(Color(0xFF2f3947))
                ) {
                    Text("Purchase", color = Color.White)
                }
            }
        }

    }
}

@Preview
@Composable
fun prev() {
    UpwardPopUpCard(size = 400.dp)
}


