package com.elfak.andjelanikolic.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(controller: NavController) {
    Column(
        modifier = Modifier
            .padding(top = 40.dp)
    ) {
        Text(text = "Login")
        Button(onClick = {
            controller.navigate("register_screen")
        }) {
            Text(text = "Register")
        }
    }
}