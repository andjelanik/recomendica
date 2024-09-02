package com.elfak.andjelanikolic.screens.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.elfak.andjelanikolic.ui.theme.background
import com.elfak.andjelanikolic.ui.theme.tertiary
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.ui.text.font.FontWeight
import com.elfak.andjelanikolic.ui.theme.primary

@Composable
fun FilterScreen(controller: NavController, filterViewModel: FilterViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(vertical = 48.dp, horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = filterViewModel.title ?: "",
            onValueChange = { filterViewModel.title = it },
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
            value = filterViewModel.description ?: "",
            onValueChange = { filterViewModel.description = it },
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
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = filterViewModel.radius ?: "",
            onValueChange = { filterViewModel.radius = it },
            label = { Text("Radius (meters)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            controller.popBackStack()
        },
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp),
            colors = ButtonColors(primary, tertiary, primary.copy(alpha = 0.6f), tertiary.copy(alpha = 0.6f))
        ) {
            Text(text = "Save", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}