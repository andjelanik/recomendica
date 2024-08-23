package com.elfak.andjelanikolic.screens.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elfak.andjelanikolic.ui.theme.background
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.elfak.andjelanikolic.ui.theme.backgroundDarker
import com.elfak.andjelanikolic.ui.theme.primary
import com.elfak.andjelanikolic.ui.theme.primaryTransparent

@Composable
fun LeaderboardScreen(controller: NavController) {
    val leaderboardViewModel = viewModel<LeaderboardViewModel>()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(top = 48.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items = leaderboardViewModel.users) { user ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundDarker, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = user.avatar,
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(32.dp)
                        .height(32.dp)
                        .background(color = primaryTransparent, shape = CircleShape)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(user.name, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .background(primary, shape = RoundedCornerShape(16.dp))
                        .padding(vertical = 6.dp, horizontal = 12.dp)
                ) {
                    Text(user.points.toString(), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
