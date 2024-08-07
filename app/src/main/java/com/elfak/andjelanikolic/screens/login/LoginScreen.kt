package com.elfak.andjelanikolic.screens.login

import android.Manifest
import android.util.Patterns
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elfak.andjelanikolic.R
import com.elfak.andjelanikolic.screens.register.RegisterState
import com.elfak.andjelanikolic.screens.register.RegisterViewModel
import com.elfak.andjelanikolic.ui.theme.background
import com.elfak.andjelanikolic.ui.theme.primary
import com.elfak.andjelanikolic.ui.theme.primaryTransparent
import com.elfak.andjelanikolic.ui.theme.tertiary
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LoginScreen(controller: NavController) {
    val loginViewModel = viewModel<LoginViewModel>()
    val context = LocalContext.current
    val permissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA
        )
    )

    var valid by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        permissions.launchMultiplePermissionRequest()
    }

    LaunchedEffect(loginViewModel.state) {
        when (loginViewModel.state) {
            is LoginState.Success -> {
                loginViewModel.state = LoginState.Idle
                controller.navigate("home_screen") {
                    popUpTo(controller.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
            is LoginState.Error -> {
                Toast.makeText(context, "Invalid email/password", Toast.LENGTH_LONG).show()
            }
            else -> { }
        }
    }

    LaunchedEffect(loginViewModel.email, loginViewModel.password) {
        if (!Patterns.EMAIL_ADDRESS.matcher(loginViewModel.email).matches()) {
            valid = false
            return@LaunchedEffect
        }

        if (loginViewModel.password.isEmpty()) {
            valid = false
            return@LaunchedEffect
        }

        valid = true
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(background),
        horizontalAlignment = Alignment.CenterHorizontally
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
        Text(text = "Recomendica", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = tertiary, fontStyle = FontStyle.Italic)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
        ) {
            Column {
                OutlinedTextField(
                    value = loginViewModel.email,
                    onValueChange = { loginViewModel.email = it },
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
                    value = loginViewModel.password,
                    onValueChange = { loginViewModel.password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                    loginViewModel.login()
                },
                    enabled = valid,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    colors = ButtonColors(primary, tertiary, primary.copy(alpha = 0.6f), tertiary.copy(alpha = 0.6f))
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