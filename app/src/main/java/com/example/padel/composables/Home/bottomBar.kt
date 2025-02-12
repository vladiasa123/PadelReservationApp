package com.example.padel.composables.Home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.padel.data.BottomBarIcons

val bottomBarIcons = listOf(
    BottomBarIcons.Home, BottomBarIcons.Booking, BottomBarIcons.MyReservations,
)

@Composable
fun TopBar(title: String, navController: NavController) {
    val currentBackStackEntry = navController.currentBackStackEntry
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = {
            if (!navController.popBackStack() ) {
                navController.navigate("Home")
            }
        }) {
        Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "go back",
                tint = Color.White
            )
        }
        Text(title, fontSize = 25.sp, color = Color.White)
        IconButton(onClick = { navController.navigate("screenC") }) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "go back",
                tint = Color.White
            )
        }
    }
}

@Composable
fun TopBarWithDynamicTitle(selectedTab: String, navController: NavController) {
    TopBar(title = selectedTab, navController = navController)
}

@Composable
fun BottomBar(
    navController: NavController, modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("Home") }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
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
                    AddItem(screen = bottomBarIcons[index],
                        navController = navController,
                        selectedTab = selectedTab,
                        onTabSelected = { title -> selectedTab = title })
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
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    BottomBarItem(
        screen = screen,
        navController = navController,
        modifier = modifier,
        onTabSelected = onTabSelected,
        isSelected = selectedTab == screen.title
    )
}


@Composable
fun BottomBarItem(
    screen: BottomBarIcons,
    navController: NavController,
    modifier: Modifier = Modifier,
    onTabSelected: (String) -> Unit,
    isSelected: Boolean
) {
    val animateSelectedIndicator by animateDpAsState(
        targetValue = if (isSelected) 50.dp else 0.dp, animationSpec = spring(
            dampingRatio = 0.5f, stiffness = 300f
        ), label = ""
    )

    Box(
        modifier = modifier
            .height(80.dp)
            .width(80.dp)
    ) {
        Box(
            modifier = Modifier
                .height(40.dp)
                .width(animateSelectedIndicator)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF3f4c60))
                .align(Alignment.Center)
        )
        IconButton(
            onClick = {
                navController.navigate(screen.route)
                onTabSelected(screen.title)
            }, modifier = Modifier.align(Alignment.Center)
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

