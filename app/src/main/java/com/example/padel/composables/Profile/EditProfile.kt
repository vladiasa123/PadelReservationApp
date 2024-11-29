package com.example.padel.composables.Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(modifier: Modifier = Modifier, onClick: () -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        Column(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(30.dp)
                )
                .height(300.dp), verticalArrangement = Arrangement.Center
        ) {
            Text(text = "New email", modifier = Modifier.padding(bottom = 5.dp, start = 10.dp))
            TextField(
                modifier = Modifier.padding(10.dp),
                value = text,
                onValueChange = {
                    text = it
                },
                label = { Text(text = "Your Label") },
                placeholder = { Text(text = "Your Placeholder/Hint") },
            )
            Text(text = "New password", modifier = Modifier.padding(bottom = 5.dp, start = 10.dp))
            TextField(
                modifier = Modifier.padding(10.dp),
                value = text,
                onValueChange = {
                    text = it
                },
                label = { Text(text = "Your Label") },
                placeholder = { Text(text = "Your Placeholder/Hint") },
            )
            Button(onClick = { onClick }, modifier = Modifier.width(250.dp).padding(top = 10.dp).align(
                Alignment.CenterHorizontally))  {
                Text(text = "Edit Profile")
            }
        }
    }
}


