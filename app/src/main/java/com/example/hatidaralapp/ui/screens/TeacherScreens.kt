package com.example.hatidaralapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun TeacherDashboardScreen(go: (String) -> Unit) {
    ScreenScaffold("My Lessons", subtitle = "Teacher Mode", bottomBar = { TeacherBar(Routes.T_HOME, go) }) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatCard("4", "Sent", Navy, Modifier.weight(1f))
            StatCard("3", "Received", Green, Modifier.weight(1f))
            StatCard("1", "Pending", Orange, Modifier.weight(1f))
        }
        SectionTitle("ACTIVE LESSON")
        AppCard {
            Text("Module 3: Fractions", fontWeight = FontWeight.SemiBold)
            Text("3 of 5 students received", color = TextMuted, fontSize = 12.sp)
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { 0.6f }, color = Green, trackColor = Mint,
                modifier = Modifier.fillMaxWidth().height(6.dp)
            )
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                StatusChip("Lesson", Navy); StatusChip("Quiz", Blue); StatusChip("Pending", Orange)
            }
            Spacer(Modifier.height(12.dp))
            PrimaryButton("Send to nearby devices", { go(Routes.T_SEND) })
        }
        SectionTitle("RECENT")
        AccentRow("Module 2: Decimals", "Sent to 5 devices", "Sent", Navy)
        AccentRow("Quiz 1: Numbers", "Received 3 of 5", "Received", Green)
        AccentRow("Module 1: Intro", "Waiting for 1 device", "Pending", Orange)
        Spacer(Modifier.height(16.dp))
        PrimaryButton("+ New Lesson", { go(Routes.T_CREATE) })
        Spacer(Modifier.height(8.dp))
        OutlineButton("+ New Quiz", { go(Routes.T_QUIZ) })
    }
}

@Composable
fun CreateLessonScreen(go: (String) -> Unit, back: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    ScreenScaffold("Create Lessons", back, "Saved on this device", bottomBar = { TeacherBar(Routes.T_CREATE, go) }) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            StatusChip("Lesson", Navy); StatusChip("Offline", Green)
        }
        Spacer(Modifier.height(8.dp))
        LabeledField("Title", title, { title = it })
        LabeledField("Subject / Topic", subject, { subject = it })
        LabeledField("Lesson text", body, { body = it }, minLines = 5)
        OutlineButton("+ Add image or document", { /* TODO file picker */ })
        Spacer(Modifier.height(8.dp))
        OutlineButton("+ Add video", { /* TODO file picker */ })
        Spacer(Modifier.height(20.dp))
        PrimaryButton("Save to Local Storage", { back() }, color = Green)
    }
}

@Composable
fun CreateQuizScreen(go: (String) -> Unit, back: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var question by remember { mutableStateOf("") }
    val options = remember { mutableStateListOf("", "", "", "") }
    var correct by remember { mutableIntStateOf(0) }
    ScreenScaffold("Create Quizzes", back, "Pick the correct answer", bottomBar = { TeacherBar(Routes.T_CREATE, go) }) {
        LabeledField("Quiz title", title, { title = it })
        LabeledField("Question", question, { question = it }, minLines = 2)
        options.forEachIndexed { i, text ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = correct == i, onClick = { correct = i },
                    colors = RadioButtonDefaults.colors(selectedColor = Green)
                )
                Box(Modifier.weight(1f)) {
                    LabeledField("Choice ${'A' + i}", text, { options[i] = it })
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        OutlineButton("+ Add question", { /* TODO add another question */ })
        Spacer(Modifier.height(16.dp))
        PrimaryButton("Save Quiz", { back() }, color = Green)
    }
}

private data class Device(val name: String, val state: String)

@Composable
fun TeacherSendScreen(go: (String) -> Unit, back: () -> Unit) {
    val devices = remember {
        mutableStateListOf(Device("Student 1", "Ready"), Device("Student 2", "Ready"), Device("Student 3", "Ready"))
    }
    ScreenScaffold("Send via Bluetooth", back, "Searching for nearby student devices...", bottomBar = { TeacherBar(Routes.T_SEND, go) }) {
        Radar()
        SectionTitle("DEVICES FOUND")
        devices.forEachIndexed { i, d ->
            AppCard(Modifier.padding(vertical = 4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(d.name, fontWeight = FontWeight.SemiBold)
                        Text("Nearby", color = TextMuted, fontSize = 12.sp)
                    }
                    when (d.state) {
                        "Ready" -> Button(
                            onClick = { devices[i] = d.copy(state = "Sent") },
                            colors = ButtonDefaults.buttonColors(containerColor = Navy)
                        ) { Text("Send") }
                        else -> StatusChip("Sent", Green)
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        InfoBox("Please keep devices within 10 meters while sending.")
        Spacer(Modifier.height(16.dp))
        OutlineButton("Stop Searching", back)
    }
}

@Composable
fun ScanQrScreen(go: (String) -> Unit, back: () -> Unit) {
    Column(Modifier.fillMaxSize().background(Color(0xFF1B1F23))) {
        Column(
            Modifier.weight(1f).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
        ) {
            Text("Scan Student QR", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Point the camera at the student's QR code", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
            Spacer(Modifier.height(24.dp))
            // TODO(integration): replace with CameraX preview + ZXing/ML Kit scanner
            Box(Modifier.size(220.dp).border(3.dp, Color.White, RoundedCornerShape(16.dp)))
        }
        Surface(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp), color = Color.White) {
            Column(Modifier.fillMaxWidth().padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Answer Record Found", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    StatusChip("Valid", Green)
                }
                Spacer(Modifier.height(10.dp))
                listOf("Student" to "Student 1", "Attempt ID" to "ATT-0001", "Lesson" to "Module 3: Fractions", "Score" to "8 / 10")
                    .forEach { (k, v) ->
                        Row(Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
                            Text(k, color = TextMuted, modifier = Modifier.weight(1f), fontSize = 13.sp)
                            Text(v, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        }
                    }
                Spacer(Modifier.height(14.dp))
                PrimaryButton("View Scores", { go(Routes.T_GRADES) })
                TextButton(onClick = back, modifier = Modifier.align(Alignment.CenterHorizontally)) { Text("Cancel") }
            }
        }
    }
}

private data class GradeItem(val name: String, val attempt: String, val score: String, val status: String)

@Composable
fun TeacherGradebookScreen(go: (String) -> Unit) {
    var filter by remember { mutableStateOf("All") }
    val items = listOf(
        GradeItem("Student 1", "ATT-0001", "8/10", "Received"),
        GradeItem("Student 2", "ATT-0002", "9/10", "Received"),
        GradeItem("Student 3", "ATT-0003", "3/10", "Flagged"),
        GradeItem("Student 4", "ATT-0004", "7/10", "Received"),
        GradeItem("Student 5", "ATT-0005", "5/10", "Flagged")
    ).filter { filter == "All" || it.status == filter }
    ScreenScaffold("Gradebook", bottomBar = { TeacherBar(Routes.T_GRADES, go) }) {
        FilterTabs(listOf("All", "Received", "Flagged"), filter) { filter = it }
        Spacer(Modifier.height(12.dp))
        items.forEach {
            AccentRow(
                it.name, "${it.attempt}  •  Score ${it.score}", it.status,
                if (it.status == "Flagged") Red else Green
            )
        }
        Spacer(Modifier.height(16.dp))
        PrimaryButton("Scan Student QR", { go(Routes.T_SCAN) })
        Spacer(Modifier.height(10.dp))
        InfoBox("Save Gradebook to local storage to keep a copy on this device.")
    }
}
