package com.example.hatidaralapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hatidaralapp.ui.components.*
import com.example.hatidaralapp.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onDone: () -> Unit) {
    LaunchedEffect(Unit) { delay(1500); onDone() }
    Box(
        Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Navy, Color(0xFF14508F)))),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier.size(96.dp).background(Color.White.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Default.School, null, tint = Color.White, modifier = Modifier.size(52.dp)) }
            Spacer(Modifier.height(16.dp))
            Text("HatidAral", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text("Offline Modular Classroom", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
        }
    }
}

@Composable
fun RoleSelectScreen(onBack: () -> Unit, onContinue: (String) -> Unit) {
    var selected by remember { mutableStateOf("Teacher") }
    ScreenScaffold("I am a...", onBack, "Choose how you'll use HatidAral on this device.") {
        RoleCard("Teacher", "Create lessons, send files to nearby students and grade answers.",
            Icons.Default.Person, selected == "Teacher") { selected = "Teacher" }
        Spacer(Modifier.height(12.dp))
        RoleCard("Student", "Receive lessons, answer offline and send your answers back.",
            Icons.Default.School, selected == "Student") { selected = "Student" }
        Spacer(Modifier.height(32.dp))
        PrimaryButton("Continue", onClick = { onContinue(selected) })
    }
}

@Composable
private fun RoleCard(title: String, desc: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        onClick = onClick, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(2.dp, if (isSelected) Navy else Color.Transparent)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(44.dp).background(Green.copy(alpha = 0.25f), CircleShape),
                contentAlignment = Alignment.Center
            ) { Icon(icon, null, tint = Navy) }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Text(desc, color = TextMuted, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun PermissionsScreen(onBack: () -> Unit, onAccept: () -> Unit) {
    ScreenScaffold("Allow nearby sharing", onBack, "HatidAral needs these to share lessons without internet.") {
        PermissionCard(Icons.Default.Bluetooth, "Bluetooth", "Find and connect to nearby devices")
        PermissionCard(Icons.Default.CameraAlt, "Camera", "Scan student QR codes")
        PermissionCard(Icons.Default.Storage, "Offline Storage", "Save lessons and grades on this device")
        Spacer(Modifier.height(8.dp))
        InfoBox("Privacy note: nothing leaves your device unless you send it to a nearby device.")
        Spacer(Modifier.height(24.dp))
        // TODO(integration): request BLUETOOTH_SCAN / CONNECT / ADVERTISE + CAMERA here before calling onAccept
        PrimaryButton("Accept and continue", onAccept)
        TextButton(onClick = onAccept, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Not now", color = TextMuted)
        }
    }
}

@Composable
private fun PermissionCard(icon: ImageVector, title: String, desc: String) {
    AppCard(Modifier.padding(vertical = 5.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(40.dp).background(Green.copy(alpha = 0.25f), CircleShape),
                contentAlignment = Alignment.Center
            ) { Icon(icon, null, tint = Navy) }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(title, fontWeight = FontWeight.SemiBold)
                Text(desc, color = TextMuted, fontSize = 12.sp)
            }
        }
    }
}
