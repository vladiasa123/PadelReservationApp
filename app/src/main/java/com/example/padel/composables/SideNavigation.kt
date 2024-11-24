package com.example.padel.composables

import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.padel.ViewModels.CalendarViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SideNav(drawerState: DrawerState, scope: CoroutineScope) {
    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet {
            IconButton(onClick = { scope.launch { drawerState.close() } }) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = "Close Menu")
            }
            Text("Drawer title", modifier = Modifier.padding(16.dp))
            NavigationDrawerItem(label = { Text(text = "Drawer Item") },
                selected = false,
                onClick = {
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else {
                            drawerState.close()
                        }
                    }
                })
        }
    }) {

        Scaffold(topBar = {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu Icon")
                }
                Text(
                    text = "Padel",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }

        }, content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Text(
                    "Choose a date",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                )
                val viewModel: CalendarViewModel = viewModel()
                PadelDatesLazy(
                    modifier = Modifier, viewModel = viewModel
                )
                Text(
                    "Choose a hour",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 10.dp, bottom = 5.dp, top = 40.dp)
                )
                val density = LocalDensity.current
                AnimatedVisibility(
                    visible = viewModel.pressedState,
                    enter = slideInVertically(
                        initialOffsetY = { with(density) { -40.dp.roundToPx() } }
                    ) + expandVertically(
                        expandFrom = Alignment.Top
                    ) + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    HourSelectionGrid(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                            .border(2.dp, MaterialTheme.colorScheme.inverseOnSurface)
                    )
                }
            }
        })

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SideNavi() {
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var scope = rememberCoroutineScope()
    SideNav(drawerState = drawerState, scope = scope)
}


