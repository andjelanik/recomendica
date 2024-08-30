package com.elfak.andjelanikolic.screens.pins

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elfak.andjelanikolic.ui.theme.background
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.elfak.andjelanikolic.ui.theme.backgroundDarker
import com.elfak.andjelanikolic.ui.theme.primary
import com.elfak.andjelanikolic.ui.theme.primaryTransparent
import com.elfak.andjelanikolic.ui.theme.secondary
import com.elfak.andjelanikolic.ui.theme.tertiary

@Composable
fun PinsScreen(controller: NavController, topController: NavController) {
    val viewModelFactory = PinViewModelFactory(LocalContext.current.applicationContext)
    val pinViewModel: PinViewModel = viewModel(factory = viewModelFactory)

    LaunchedEffect(Unit) {
        pinViewModel.loadPins()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(items = pinViewModel.pins) { pin ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundDarker, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = pin.photo,
                            contentDescription = "Selected Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(64.dp)
                                .height(64.dp)
                                .background(color = primaryTransparent, shape = RoundedCornerShape(16.dp))
                                .clip(RoundedCornerShape(16.dp))
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(pin.title, fontWeight = FontWeight.Bold)
                            Text(pin.description)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        FloatingActionButton(
            containerColor = primary,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            onClick = {
                topController.navigate("create_screen/${pinViewModel.currentLocation?.latitude}/${pinViewModel.currentLocation?.longitude}")
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Create",
                tint = tertiary
            )
        }
    }
}