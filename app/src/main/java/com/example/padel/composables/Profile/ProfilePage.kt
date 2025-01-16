package com.example.padel.composables.Profile

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.padel.R
import com.example.padel.ViewModels.JwtTokenViewModel
import com.example.padel.ViewModels.ProfileViewModel
import com.example.padel.composables.HeaderText
import com.example.padel.composables.Home.BottomNavigation
import com.example.padel.composables.Home.SideNavigation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
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
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = CenterHorizontally
        ) {
            var viewModel: ProfileViewModel = viewModel()
            var jwtViewModel: JwtTokenViewModel = viewModel()
            ProfilePage(viewModel =  viewModel, jwtViewModel = jwtViewModel, paddingValues = paddingValues)
        }
    })
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(viewModel: ProfileViewModel, jwtViewModel: JwtTokenViewModel, paddingValues: PaddingValues = PaddingValues()) {

    val scope = rememberCoroutineScope( )
    val context = LocalContext.current

    Box(modifier = Modifier.background(MaterialTheme.colorScheme.primary)) {
        val animateState = viewModel.animatedState
        HeaderText(viewModel)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .align(Alignment.BottomCenter)
                .then(if (animateState.value) Modifier.blur(20.dp) else Modifier)

        ) {
            Box(
                modifier = Modifier
                    .height(550.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .align(Alignment.BottomCenter)

            ) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    if (animateState.value == false) {
                        UserInfo(text = "Vlad Corpodean")
                        UserInfo(text = "VladCorpodaen")
                        UserInfo(text = "VladCorpodaen")
                    }
                    Box(
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(top = 50.dp)
                    ) {
                        Button(
                            onClick = { viewModel.animatedState.value = true },
                            modifier = Modifier.width(200.dp)
                        ) {
                            Text("Edit profile")
                        }
                    }

                }
            }
        }
        val offsetY by animateDpAsState(
            targetValue = if (!animateState.value) (-110).dp else (-300).dp,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            label = ""
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = offsetY)
        ) {
            Card(
                modifier = Modifier
                    .size(150.dp)
                    .border(2.dp, MaterialTheme.colorScheme.tertiary, CircleShape)
                    .clip(CircleShape)

            ) {
                Image(
                    painter = painterResource(R.drawable.profile_image),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

            }
        }
        if (animateState.value == true) {
            EditProfile(modifier = Modifier, onClick = { viewModel.animatedState.value = false })
        }
    }
}




