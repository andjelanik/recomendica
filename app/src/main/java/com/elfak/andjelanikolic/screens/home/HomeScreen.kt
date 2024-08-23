package com.elfak.andjelanikolic.screens.home

import android.Manifest
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.elfak.andjelanikolic.R
import com.elfak.andjelanikolic.navigation.BottomBarGraph
import com.elfak.andjelanikolic.repositories.AuthRepository
import com.elfak.andjelanikolic.ui.theme.background
import com.elfak.andjelanikolic.ui.theme.primary
import com.elfak.andjelanikolic.ui.theme.tertiary
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@Composable
fun HomeScreen(controller: NavController) {
    val nestedController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(controller = nestedController)
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
        ) {
            BottomBarGraph(controller = nestedController, topController = controller as NavHostController)
        }
    }
}