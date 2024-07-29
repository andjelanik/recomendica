package com.elfak.andjelanikolic.screens.login

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.elfak.andjelanikolic.R
import com.elfak.andjelanikolic.ui.theme.background
import com.elfak.andjelanikolic.ui.theme.primary
import com.elfak.andjelanikolic.ui.theme.primaryTransparent
import com.elfak.andjelanikolic.ui.theme.tertiary
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LoginScreen(controller: NavController) {
    val permissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA
        )
    )

    LaunchedEffect(Unit) {
        permissions.launchMultiplePermissionRequest()
    }

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(background)
    ) {
        Spacer(modifier = Modifier.height(256.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.hot_air_balloon_stroke_rounded),
                contentDescription = "Logo",
                tint = tertiary
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
        ) {
            Column {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = tertiary,
                        unfocusedTextColor = tertiary,
                        focusedContainerColor = background,
                        unfocusedContainerColor = background,
                        unfocusedLabelColor = tertiary,
                        focusedLabelColor = tertiary,
                        focusedBorderColor = tertiary,
                        unfocusedBorderColor = tertiary
                    ),
                    textStyle = TextStyle.Default.copy(
                        fontSize = 16.sp
                    ),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = tertiary,
                        unfocusedTextColor = tertiary,
                        focusedContainerColor = background,
                        unfocusedContainerColor = background,
                        unfocusedLabelColor = tertiary,
                        focusedLabelColor = tertiary,
                        focusedBorderColor = tertiary,
                        unfocusedBorderColor = tertiary
                    ),
                    textStyle = TextStyle.Default.copy(
                        fontSize = 16.sp
                    ),
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {

                },
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    colors = ButtonColors(primary, tertiary, primary, tertiary)
                ) {
                    Text(text = "Login", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = {
                    controller.navigate("register_screen")
                }, colors = ButtonColors(Color.Transparent, tertiary, Color.Transparent, tertiary)) {
                    Text(text = "Don't have an account? Register", fontSize = 16.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(64.dp))
    }
}