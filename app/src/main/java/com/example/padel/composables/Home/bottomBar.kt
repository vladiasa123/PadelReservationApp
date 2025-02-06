package com.example.padel.composables.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.padel.ViewModels.ProfileViewModel
import com.example.padel.data.BottomBarIcons

val bottomBarIcons = listOf(
    BottomBarIcons.Home, BottomBarIcons.Booking, BottomBarIcons.MyReservations,
)
var selectedTab by  mutableStateOf("Home")

@Composable
fun TopBar(title: String) {
    var color = Color(0xFFFFFFFF)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "go back",
                tint = Color.White
            )
        }
        Text(title, fontSize = 25.sp, color = color)
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "go back",
                tint = Color.White
            )
        }
    }
}


@Composable
fun TopBarWithDynamicTitle() {
    TopBar(title = selectedTab )
}

@Composable
fun BottomBar(navController: NavController, modifier: Modifier = Modifier) {

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFF262e3a))
        ) {
            LazyRow(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                items(bottomBarIcons.size) { index ->
                    AddItem(
                        screen = bottomBarIcons[index],
                        navController = navController,
                        onTabSelected = { title -> selectedTab = title }
                    )
                }
            }
        }
    }
}




@Composable
fun AddItem(
    screen: BottomBarIcons,
    navController: NavController,
    modifier: Modifier = Modifier,
    onTabSelected: (String) -> Unit
) {
    BottomBarItem(
        screen = screen,
        navController = navController,
        modifier = modifier,
        onTabSelected = onTabSelected
    )
}

@Composable
fun BottomBarItem(
    screen: BottomBarIcons,
    navController: NavController,
    modifier: Modifier = Modifier,
    onTabSelected: (String) -> Unit
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .width(80.dp)
    ) {
        Box(
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            IconButton(
                onClick = {
                    navController.navigate(screen.route)
                    onTabSelected(screen.title) // Update the selected tab title
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = screen.title,
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        }
    }
}


