package com.example.hatidaralapp.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hatidaralapp.ui.components.*
import com.example.hatidaralapp.ui.theme.*

@Composable
fun StudentPairScreen(onAccept: () -> Unit, onDecline: () -> Unit) {
    ScreenScaffold("HatidAral", subtitle = "Student") {
        Box(
            Modifier.size(64.dp).background(Navy, CircleShape).align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) { Icon(Icons.Default.School, null, tint = Color.White, modifier = Modifier.size(34.dp)) }
        Spacer(Modifier.height(16.dp))
        AppCard {
            Text("Dr. Cruzly wants to send", fontWeight = FontWeight.SemiBold)
            Text("Module 3: Fractions (Lesson + Quiz)", color = TextMuted, fontSize = 12.sp)
            Spacer(Modifier.height(14.dp))
            Text("Enter the code shown on the teacher's device", fontSize = 12.sp, color = TextMuted)
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)) {
                "1234".forEach { d ->
                    Box(
                        Modifier.size(width = 46.dp, height = 54.dp).background(Mint, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) { Text(d.toString(), fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Navy) }
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        PrimaryButton("Accept and Save", onAccept)
        Spacer(Modifier.height(8.dp))
        OutlineButton("Decline", onDecline)
        Spacer(Modifier.height(12.dp))
        InfoBox("Files are saved on this device and work without internet.")
    }
}

@Composable
fun StudentLessonsScreen(go: (String) -> Unit) {
    ScreenScaffold("My Lessons", subtitle = "Saved Offline", bottomBar = { StudentBar(Routes.S_HOME, go) }) {
        SectionTitle("LESSONS")
        AccentRow("Module 3: Fractions", "Dr. Cruzly", "Open", Navy) { go(Routes.S_SUBMIT) }
        AccentRow("Module 2: Decimals", "Dr. Cruzly", "Open", Navy) { go(Routes.S_SUBMIT) }
        AccentRow("Module 1: Intro", "Dr. Cruzly", "Open", Navy) { go(Routes.S_SUBMIT) }
        SectionTitle("QUIZZES")
        AccentRow("Quiz 1: Numbers", "10 items", "Answer", Blue) { go(Routes.S_SUBMIT) }
        AccentRow("Quiz 2: Fractions", "8 items", "Answer", Blue) { go(Routes.S_SUBMIT) }
        AccentRow("Quiz 3: Decimals", "5 items", "Done", Green)
        Spacer(Modifier.height(16.dp))
        OutlineButton("Upload Files", { /* TODO file picker */ })
    }
}

@Composable
fun SubmissionScreen(go: (String) -> Unit, back: () -> Unit) {
    ScreenScaffold("Submission", back, "Send your answers back", bottomBar = { StudentBar(Routes.S_SUBMIT, go) }) {
        AppCard {
            Text("Send via Bluetooth", fontWeight = FontWeight.Bold)
            Text("Send your answers directly to the teacher's device.", color = TextMuted, fontSize = 12.sp)
            Spacer(Modifier.height(12.dp))
            PrimaryButton("Send Now", { go(Routes.S_SEND) })
        }
        Spacer(Modifier.height(12.dp))
        AppCard {
            Text("Show QR Code", fontWeight = FontWeight.Bold)
            Text("Let the teacher scan your answer record.", color = TextMuted, fontSize = 12.sp)
            Spacer(Modifier.height(12.dp))
            OutlineButton("Show QR", { go(Routes.S_QR) })
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            StatusChip("Saved", Green); StatusChip("Ready", Blue); StatusChip("Pending", Orange)
        }
        Spacer(Modifier.height(8.dp))
        InfoBox("Your answers are saved on this device even if sending fails.")
    }
}

@Composable
fun StudentSendScreen(go: (String) -> Unit, back: () -> Unit) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(Unit) { progress.animateTo(1f, tween(5000)) }
    ScreenScaffold("Send via Bluetooth", back, "Connected to your teacher", bottomBar = { StudentBar(Routes.S_SUBMIT, go) }) {
        Radar()
        AppCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Teacher Found", fontWeight = FontWeight.SemiBold)
                    Text("Dr. Cruzly", color = TextMuted, fontSize = 12.sp)
                }
                StatusChip("Connected", Green)
            }
            Spacer(Modifier.height(14.dp))
            val pct = (progress.value * 100).toInt()
            Text(if (pct < 100) "Sending Answers... $pct%" else "Answers sent!", fontSize = 13.sp)
            Spacer(Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { progress.value }, color = Green, trackColor = Mint,
                modifier = Modifier.fillMaxWidth().height(8.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        InfoBox("Keep this screen open until sending is complete.", Orange)
        Spacer(Modifier.height(16.dp))
        if (progress.value >= 1f) PrimaryButton("Done", { go(Routes.S_GRADES) })
        else OutlineButton("Cancel", back)
    }
}

@Composable
fun ShowQrScreen(go: (String) -> Unit, back: () -> Unit) {
    ScreenScaffold("Show QR Code", back, "Let your teacher scan this", bottomBar = { StudentBar(Routes.S_SUBMIT, go) }) {
        AppCard {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                QrPlaceholder()
                Spacer(Modifier.height(8.dp))
                Text("Attempt ID", color = TextMuted, fontSize = 12.sp)
                Text("ATT-0001", fontWeight = FontWeight.Bold)
            }
        }
        Spacer(Modifier.height(12.dp))
        InfoBox("Waiting for teacher to scan. Hold your screen steady.", Orange)
        Spacer(Modifier.height(16.dp))
        OutlineButton("Send via Bluetooth", { go(Routes.S_SEND) })
        Spacer(Modifier.height(8.dp))
        PrimaryButton("Done", { go(Routes.S_HOME) })
    }
}

private data class StudentGrade(val title: String, val score: String, val status: String)

@Composable
fun StudentGradebookScreen(go: (String) -> Unit) {
    var filter by remember { mutableStateOf("All") }
    val items = listOf(
        StudentGrade("Quiz 1: Numbers", "9/10", "Graded"),
        StudentGrade("Quiz 2: Fractions", "—", "Pending"),
        StudentGrade("Quiz 3: Decimals", "7/10", "Graded"),
        StudentGrade("Module 3 Activity", "—", "Pending")
    ).filter { filter == "All" || it.status == filter }
    ScreenScaffold("Gradebook", bottomBar = { StudentBar(Routes.S_GRADES, go) }) {
        FilterTabs(listOf("All", "Graded", "Pending"), filter) { filter = it }
        Spacer(Modifier.height(12.dp))
        items.forEach {
            AccentRow(
                it.title, "Score ${it.score}", it.status,
                if (it.status == "Graded") Green else Orange
            )
        }
        Spacer(Modifier.height(16.dp))
        PrimaryButton("Back to My Lessons", { go(Routes.S_HOME) })
    }
}
