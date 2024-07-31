package com.elfak.andjelanikolic.screens.register

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.elfak.andjelanikolic.ui.theme.background
import com.elfak.andjelanikolic.ui.theme.primary
import com.elfak.andjelanikolic.ui.theme.primaryTransparent
import com.elfak.andjelanikolic.ui.theme.secondary
import com.elfak.andjelanikolic.ui.theme.tertiary
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RegisterScreen(controller: NavController) {
    val context = LocalContext.current
    val registerViewModel = viewModel<RegisterViewModel>()
    var file by remember {
        mutableStateOf<File?>(null)
    }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            if (it != null) {
                registerViewModel.photo = it
            }
        }
    )
    val camera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success: Boolean ->
            if (success) {
                file?.let {
                    registerViewModel.photo = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        it
                    )
                }
            }
        }
    )

    LaunchedEffect(registerViewModel.state) {
        when (registerViewModel.state) {
            is RegisterState.Success -> {
                controller.navigate("home_screen") {
                    popUpTo(controller.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
            else -> { }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(128.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = registerViewModel.photo,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(96.dp)
                    .height(96.dp)
                    .background(color = primaryTransparent, shape = CircleShape)
                    .clip(CircleShape)
            )
            Row {
                TextButton(onClick = {
                    imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }, colors = ButtonColors(Color.Transparent, secondary, Color.Transparent, secondary)) {
                    Text(text = "Gallery", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(24.dp))
                TextButton(onClick = {
                    file = createImageFile(context)
                    val uri = file?.let {
                        FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.fileprovider",
                            it
                        )
                    }
                    uri?.let { camera.launch(it) }
                }, colors = ButtonColors(Color.Transparent, secondary, Color.Transparent, secondary)) {
                    Text(text = "Camera", fontSize = 16.sp)
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
        ) {
            Column {
                OutlinedTextField(
                    value = registerViewModel.username,
                    onValueChange = { registerViewModel.username = it },
                    label = { Text("Username") },
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
                    value = registerViewModel.email,
                    onValueChange = { registerViewModel.email = it },
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
                    value = registerViewModel.password,
                    onValueChange = { registerViewModel.password = it },
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
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = registerViewModel.fullName,
                    onValueChange = { registerViewModel.fullName = it },
                    label = { Text("Full Name") },
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
                    value = registerViewModel.phone,
                    onValueChange = { registerViewModel.phone = it },
                    label = { Text("Phone Number") },
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
                    registerViewModel.register()
                },
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    colors = ButtonColors(primary, tertiary, primary, tertiary)
                ) {
                    Text(text = "Register", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = {
                    controller.popBackStack()
                }, colors = ButtonColors(Color.Transparent, tertiary, Color.Transparent, tertiary)) {
                    Text(text = "Already have an account? Login", fontSize = 16.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(64.dp))
    }

}

fun createImageFile(context: Context): File {
    val timeStamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG${timeStamp}_",
        ".jpg",
        storageDir
    )
}