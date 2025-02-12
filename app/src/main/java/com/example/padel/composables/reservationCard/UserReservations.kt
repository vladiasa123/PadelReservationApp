package com.example.padel.composables.reservationCard

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.padel.ViewModels.JwtTokenViewModel
import com.example.padel.api.RetrofitClient
import com.example.padel.data.ReservationResponse
import com.example.padel.data.UsersReservation
import com.example.padel.data.hourItems
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.File



@Composable
fun UserReservations(
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
    val scope = rememberCoroutineScope()
    val jwtTokenViewModel: JwtTokenViewModel = viewModel()
    var hourIntervals by remember { mutableStateOf(mutableListOf<String>()) }


    val imageFolderPath = "/data/data/com.example.padel/files/"
    val imageFiles = File(imageFolderPath).listFiles { file ->
        file.extension.lowercase() in listOf("png", "jpg", "jpeg", "gif")
    }?.toList() ?: emptyList()

    val combinedList = hourIntervals.zip(imageFiles)


    LaunchedEffect(Unit) {
        val token = jwtTokenViewModel.getToken()
        jwtTokenViewModel.decodeToken(token.toString())
        scope.launch {
            val reservationRequest = UsersReservation(jwtTokenViewModel.usersId.value)
            val response: Response<ReservationResponse> =
                RetrofitClient.apiService.getUsersReservations(reservationRequest)
            if (response.isSuccessful) {
                val reservationResponse = response.body()
                val userReservations = reservationResponse?.userSlots
                Log.d("v", userReservations.toString())
                if (userReservations != null) {
                    hourIntervals = userReservations.toMutableList()
                }
                Log.d("v", hourIntervals.toString())
            }
        }
    }



    LazyRow(
         state = lazyListState, flingBehavior = snapFlingBehavior, modifier = Modifier.fillMaxWidth()
    ) {
        items(combinedList) { (hour, imageFiles) ->
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 30.dp)
            ) {
                Text(
                    text = "January 15",
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF924974), Color(0xFFe38378))
                        ),
                        fontSize = 70.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                )

                Text(
                    "2020",
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF924974), Color(0xFFe38378))
                        ),
                        fontSize = 70.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "$hour",
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(end = 150.dp, bottom = 20.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
                ElevatedCard(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF262e3a)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageFiles),
                        contentDescription = "A description of the image or null if decorative",
                        modifier = Modifier
                            .padding(top = 30.dp, bottom = 30.dp)
                            .size(150.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .align(Alignment.CenterHorizontally)
                    )
                }
                ElevatedCard(
                    modifier = modifier
                        .fillParentMaxWidth()
                        .padding(top = 40.dp, end = 10.dp, start = 10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF262e3a))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .height(60.dp)
                    ) {
                        Text(
                            "Teren 1",
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                        Box(modifier = Modifier.fillParentMaxWidth()) {
                            ElevatedButton(
                                colors = ButtonDefaults.elevatedButtonColors(
                                    containerColor = Color(0xFF262e3a),
                                    contentColor = MaterialTheme.colorScheme.primary
                                ),
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 80.dp),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Location")
                            }
                        }

                    }
                }
                Row(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillParentMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ElevatedButton(
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFF262e3a),
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = { /*TODO*/ },
                        modifier = Modifier.padding(start = 10.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Location")
                    }
                    ElevatedButton(
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFF262e3a),
                            contentColor = MaterialTheme.colorScheme.primary
                        ), onClick = { /*TODO*/ }, shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Location")
                    }
                    ElevatedButton(
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFF262e3a),
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(end = 10.dp),
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Location")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun preview() {
    UserReservations()
}




