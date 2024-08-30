package com.elfak.andjelanikolic.screens.create

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.elfak.andjelanikolic.screens.register.createImageFile
import com.elfak.andjelanikolic.ui.theme.background
import com.elfak.andjelanikolic.ui.theme.primary
import com.elfak.andjelanikolic.ui.theme.primaryTransparent
import com.elfak.andjelanikolic.ui.theme.secondary
import com.elfak.andjelanikolic.ui.theme.tertiary
import java.io.File

@Composable
fun CreateScreen(controller: NavController, latitude: Float?, longitude: Float?) {
    val createViewModel = viewModel<CreateViewModel>()
    val context = LocalContext.current
    var file by remember { mutableStateOf<File?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                createViewModel.photo = uri
            }
        }
    )
    val camera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success: Boolean ->
            if (success) {
                file?.let {
                    createViewModel.photo = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        it
                    )
                }
            }
        }
    )

    LaunchedEffect(createViewModel.state) {
        when (createViewModel.state) {
            is CreateState.Error -> {
                Toast.makeText(context, "An error has occurred.", Toast.LENGTH_SHORT).show()
            }
            is CreateState.Success -> {
                controller.popBackStack()
            }
            else -> { }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = createViewModel.photo,
                contentDescription = "Selected Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(192.dp)
                    .height(192.dp)
                    .background(color = primaryTransparent, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
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
                    value = createViewModel.title,
                    onValueChange = { createViewModel.title = it },
                    label = { Text("Title") },
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
                    value = createViewModel.description,
                    onValueChange = { createViewModel.description = it },
                    label = { Text("Description") },
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
                    minLines = 3,
                    maxLines = 3
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    if (latitude != null && longitude != null) {
                        createViewModel.create(latitude, longitude)
                    }
                },
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    colors = ButtonColors(primary, tertiary, primary.copy(alpha = 0.6f), tertiary.copy(alpha = 0.6f))
                ) {
                    Text(text = "Create", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(modifier = Modifier.height(64.dp))
    }
}