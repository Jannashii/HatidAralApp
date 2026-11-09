package com.example.hatidaralapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hatidaralapp.ui.theme.*

object Routes {
    const val SPLASH = "splash"
    const val ROLE = "role"
    const val PERMISSIONS = "permissions"
    // Teacher
    const val T_HOME = "t_home"
    const val T_CREATE = "t_create_lesson"
    const val T_QUIZ = "t_create_quiz"
    const val T_SEND = "t_send"
    const val T_SCAN = "t_scan"
    const val T_GRADES = "t_grades"
    // Student
    const val S_PAIR = "s_pair"
    const val S_HOME = "s_home"
    const val S_SUBMIT = "s_submit"
    const val S_SEND = "s_send"
    const val S_QR = "s_qr"
    const val S_GRADES = "s_grades"
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, color: Color = Navy) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) { Text(text, fontWeight = FontWeight.SemiBold) }
}

@Composable
fun OutlineButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(48.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.5.dp, Navy)
    ) { Text(text, color = Navy, fontWeight = FontWeight.SemiBold) }
}

@Composable
fun StatusChip(text: String, color: Color) {
    Surface(shape = RoundedCornerShape(50), color = color.copy(alpha = 0.15f)) {
        Text(
            text, color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun AppCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) { Column(Modifier.padding(14.dp), content = content) }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextMuted,
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )
}

@Composable
fun InfoBox(text: String, color: Color = Blue) {
    Surface(shape = RoundedCornerShape(10.dp), color = color.copy(alpha = 0.15f), modifier = Modifier.fillMaxWidth()) {
        Text(text, color = TextDark, fontSize = 12.sp, modifier = Modifier.padding(12.dp))
    }
}

@Composable
fun StatCard(value: String, label: String, color: Color, modifier: Modifier = Modifier) {
    Card(modifier, shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(Modifier.fillMaxWidth().padding(vertical = 14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = color)
            Text(label, fontSize = 12.sp, color = TextMuted)
        }
    }
}

@Composable
fun LabeledField(label: String, value: String, onChange: (String) -> Unit, minLines: Int = 1) {
    OutlinedTextField(
        value = value, onValueChange = onChange, label = { Text(label) },
        minLines = minLines, shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
            focusedBorderColor = Navy
        )
    )
}

@Composable
fun FilterTabs(options: List<String>, selected: String, onSelect: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        options.forEach { o ->
            val on = o == selected
            Surface(
                onClick = { onSelect(o) }, shape = RoundedCornerShape(50),
                color = if (on) Navy else Color.White
            ) {
                Text(
                    o, color = if (on) Color.White else Navy, fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

/** Row with a colored left bar + status chip (lessons, gradebook). */
@Composable
fun AccentRow(title: String, sub: String, chip: String, color: Color, onClick: (() -> Unit)? = null) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
    ) {
        Row(Modifier.height(IntrinsicSize.Min), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.width(5.dp).fillMaxHeight().background(color))
            Column(Modifier.weight(1f).padding(12.dp)) {
                Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(sub, color = TextMuted, fontSize = 12.sp)
            }
            Box(Modifier.padding(end = 12.dp)) { StatusChip(chip, color) }
        }
    }
}

@Composable
fun ScreenScaffold(
    title: String,
    onBack: (() -> Unit)? = null,
    subtitle: String? = null,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(containerColor = Mint, bottomBar = bottomBar) { pad ->
        Column(
            Modifier.padding(pad).fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            if (onBack != null) {
                IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
            }
            Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Navy)
            if (subtitle != null) Text(subtitle, color = TextMuted, fontSize = 13.sp)
            Spacer(Modifier.height(16.dp))
            content()
            Spacer(Modifier.height(16.dp))
        }
    }
}

data class NavItem(val route: String, val label: String, val icon: ImageVector)

@Composable
fun AppBottomBar(items: List<NavItem>, current: String, onSelect: (String) -> Unit) {
    NavigationBar(containerColor = Color.White) {
        items.forEach {
            NavigationBarItem(
                selected = it.route == current,
                onClick = { onSelect(it.route) },
                icon = { Icon(it.icon, it.label) },
                label = { Text(it.label, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Mint, selectedIconColor = Navy, selectedTextColor = Navy
                )
            )
        }
    }
}

@Composable
fun TeacherBar(current: String, go: (String) -> Unit) = AppBottomBar(
    listOf(
        NavItem(Routes.T_HOME, "Lessons", Icons.AutoMirrored.Filled.MenuBook),
        NavItem(Routes.T_CREATE, "Create", Icons.Default.EditNote),
        NavItem(Routes.T_SEND, "Send", Icons.AutoMirrored.Filled.Send),
        NavItem(Routes.T_GRADES, "Grades", Icons.Default.Grade)
    ), current, go
)

@Composable
fun StudentBar(current: String, go: (String) -> Unit) = AppBottomBar(
    listOf(
        NavItem(Routes.S_HOME, "Lessons", Icons.AutoMirrored.Filled.MenuBook),
        NavItem(Routes.S_SUBMIT, "Submit", Icons.AutoMirrored.Filled.Send),
        NavItem(Routes.S_GRADES, "Grades", Icons.Default.Grade)
    ), current, go
)

/** Bluetooth radar graphic (concentric circles). */
@Composable
fun Radar(icon: ImageVector = Icons.Default.Bluetooth) {
    Box(Modifier.fillMaxWidth().height(190.dp), contentAlignment = Alignment.Center) {
        Canvas(Modifier.size(180.dp)) {
            listOf(1f, 0.72f, 0.45f).forEachIndexed { i, s ->
                drawCircle(Navy.copy(alpha = 0.06f + i * 0.05f), radius = size.minDimension / 2 * s)
            }
        }
        Box(Modifier.size(52.dp).background(Navy, CircleShape), contentAlignment = Alignment.Center) {
            Icon(icon, null, tint = Color.White)
        }
    }
}

/** Placeholder QR drawing. Swap for a real ZXing-generated bitmap during integration. */
@Composable
fun QrPlaceholder(dim: Dp = 190.dp) {
    Canvas(Modifier.size(dim).background(Color.White)) {
        val n = 21
        val c = size.width / n
        fun finder(x: Int, y: Int) {
            drawRect(Navy, Offset(x * c, y * c), Size(7 * c, 7 * c))
            drawRect(Color.White, Offset((x + 1) * c, (y + 1) * c), Size(5 * c, 5 * c))
            drawRect(Navy, Offset((x + 2) * c, (y + 2) * c), Size(3 * c, 3 * c))
        }
        for (i in 0 until n) for (j in 0 until n) {
            val inFinder = (i < 8 && j < 8) || (i > n - 9 && j < 8) || (i < 8 && j > n - 9)
            if (!inFinder && (i * 7 + j * 13 + i * j) % 3 == 0) {
                drawRect(Navy, Offset(i * c, j * c), Size(c, c))
            }
        }
        finder(0, 0); finder(n - 7, 0); finder(0, n - 7)
    }
}
