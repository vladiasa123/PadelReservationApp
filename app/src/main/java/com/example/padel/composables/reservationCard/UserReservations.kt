package com.example.padel.composables.reservationCard

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.padel.ViewModels.CalendarViewModel
import com.example.padel.ViewModels.JwtTokenViewModel
import com.example.padel.api.RetrofitClient
import com.example.padel.composables.Home.BottomNavigation
import com.example.padel.composables.Home.SideNavigation
import com.example.padel.data.ReservationResponse
import com.example.padel.data.UsersReservation
import com.example.padel.data.hourItems
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationsScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    Scaffold(topBar = {
        if (screenWidthDp > 600) {
            SideNavigation(Modifier.Companion.zIndex(2F))
        }
    }, bottomBar = {
        if (screenWidthDp < 600) {
            BottomNavigation(navController = navController)
        }
    }, content = { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            UserReservations(
                paddingValues = paddingValues,
                viewModel = CalendarViewModel(),
            )
        }
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserReservations(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    viewModel: CalendarViewModel,
) {
    val lazyListState = rememberLazyListState()
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
    val imageLocation = "/data/data/com.example.padel/files/reservation.png"
    var hourInterval by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val scope = rememberCoroutineScope()
    val jwtTokenViewModel: JwtTokenViewModel = viewModel()
    var hourIntervals by remember { mutableStateOf(mutableListOf<String>()) }





    LaunchedEffect(Unit) {
        val token = jwtTokenViewModel.getToken()
        jwtTokenViewModel.decodeToken(token.toString())
        scope.launch {
            val reservationRequest = UsersReservation(jwtTokenViewModel.usersId.value)
            val response: Response<ReservationResponse> =
                RetrofitClient.apiService.getUsersReservations(reservationRequest)
            if (response.isSuccessful) {
                val reservationResponse = response.body()
                val userReservations = reservationResponse?.userReservations
                Log.d("v", userReservations.toString())
                if (userReservations != null) {
                    hourIntervals = userReservations.toMutableList()
                }
                Log.d("v", hourIntervals.toString())
            }
        }
    }




    LazyRow(
        state = lazyListState,
        flingBehavior = snapFlingBehavior,
        modifier = Modifier.fillMaxWidth()
    ) {
        items(hourItems) { item ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    "January 15",
                    fontSize = 70.sp,
                    modifier = Modifier.fillParentMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "2020",
                    fontSize = 70.sp,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(end = 180.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "$hourIntervals",
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(end = 150.dp, bottom = 20.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
                ElevatedCard(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 10.dp, end = 10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = File(imageLocation)),
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
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
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
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
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
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
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
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Location")
                    }
                    ElevatedButton(
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
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


