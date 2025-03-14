package com.zybooks.bookworm.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zybooks.bookworm.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    var notificationsEnabled by remember { mutableStateOf(false) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { BookwormBackHeader(navController) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo Section
                Image(
                    painter = painterResource(id = R.drawable.bookworm_logo), // Replace with your actual logo resource
                    contentDescription = "Bookworm Logo",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 8.dp)
                )

                // About Section
                Text(
                    text = "About Bookworm",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Welcome to Bookworm! This app is designed to help you manage your personal library effectively. Whether you're a novel buff or looking to get into reading, the simplicity of our features allows you to easily track your progress and set realistic reading goals.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Display Mode Section
                Text("Display Mode",
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Dark Mode Preview
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painterResource(id = R.drawable.dark_mode), // Replace with your actual drawable resource for dark mode preview.
                            contentDescription = "Dark Mode Preview",
                            modifier = Modifier.size(250.dp)
                        )
                        RadioButton(
                            selected = darkModeEnabled,
                            onClick = { darkModeEnabled = true }
                        )
                        Text("Dark", fontSize = 13.sp)
                    }

                    // Light Mode Preview
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painterResource(id = R.drawable.light_mode), // Replace with your actual drawable resource for light mode preview.
                            contentDescription = "Light Mode Preview",
                            modifier = Modifier.size(250.dp)
                        )
                        RadioButton(
                            selected = !darkModeEnabled,
                            onClick = { darkModeEnabled = false }
                        )
                        Text("Light", fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Notifications Toggle Section
                Text("Notifications",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Enable Push Notifications",
                        fontSize = 18.sp, modifier = Modifier.padding(end=4.dp))
                    Switch(checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it },
                        modifier = Modifier.padding(start=4.dp)
                    )
                }
            }
        }
    )
}
