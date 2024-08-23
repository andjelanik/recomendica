package com.elfak.andjelanikolic.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.elfak.andjelanikolic.startLocationService
import com.elfak.andjelanikolic.stopLocationService
import com.elfak.andjelanikolic.ui.theme.background
import com.elfak.andjelanikolic.ui.theme.primary
import com.elfak.andjelanikolic.ui.theme.primaryTransparent
import com.elfak.andjelanikolic.ui.theme.tertiary

@Composable
fun ProfileScreen(controller: NavController) {
    val profileViewModel = viewModel<ProfileViewModel>()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        AsyncImage(
            model = profileViewModel.user?.avatar,
            contentDescription = "Selected Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(96.dp)
                .height(96.dp)
                .background(color = primaryTransparent, shape = CircleShape)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = profileViewModel.user?.name ?: "Error",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = tertiary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = profileViewModel.user?.email ?: "Error",
            fontSize = 16.sp,
            color = tertiary
        )
        Spacer(modifier = Modifier.height(64.dp))
        Button(
            onClick = {
                if (profileViewModel.isServiceRunning) {
                    context.stopLocationService()
                    profileViewModel.isServiceRunning = false
                } else {
                    context.startLocationService()
                    profileViewModel.isServiceRunning = true
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp),
            colors = ButtonColors(primary, tertiary, primary, tertiary)
        ) {
            Text(
                text = if (profileViewModel.isServiceRunning) "Stop location tracking" else "Start location tracking",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            profileViewModel.logOut()
            controller.navigate("login_screen") {
                popUpTo("home_screen") { inclusive = true }
            }
        },
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp),
            colors = ButtonColors(primary, tertiary, primary, tertiary)
        ) {
            Text(text = "Logout", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}