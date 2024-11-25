package com.example.padel.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginPage(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.background(MaterialTheme.colorScheme.primary)) {
        Column {
            Text(
                "Welcome",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(start = 10.dp, top = 20.dp),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background
            )
            Text(
                "Back",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .height(550.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    TextFieldWithIcons(modifier = Modifier, "Email or Username", Icons.Filled.Email)
                    TextFieldWithIcons(modifier = Modifier, "Password", Icons.Filled.Lock)
                    Text("Forgot password?", modifier = Modifier.padding(start = 160.dp))
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(top = 100.dp)
                            .width(270.dp)
                            .height(40.dp),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text("Sign up")
                    }
                }


            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithIcons(modifier: Modifier, placeHolder: String, Icon: ImageVector) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var unfocusedColor = MaterialTheme.colorScheme.primary
    var focusedColor = MaterialTheme.colorScheme.tertiary
    return OutlinedTextField(
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = unfocusedColor,
            unfocusedLeadingIconColor = unfocusedColor,
            unfocusedSupportingTextColor = unfocusedColor,
            focusedBorderColor = focusedColor,
            focusedLeadingIconColor = focusedColor,
            focusedSupportingTextColor = focusedColor,
            focusedLabelColor = focusedColor
        ),
        value = text,
        leadingIcon = { Icon(imageVector = Icon, contentDescription = "emailIcon") },
        onValueChange = {
            text = it
        },
        label = { Text(text = placeHolder) },
        placeholder = { Text(text = "Enter your e-mail") },
    )
}


@Preview
@Composable
fun LoginPrev() {
    LoginPage()

}