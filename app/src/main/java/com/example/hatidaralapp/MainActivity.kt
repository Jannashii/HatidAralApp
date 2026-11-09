package com.example.hatidaralapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hatidaralapp.ui.components.Routes
import com.example.hatidaralapp.ui.screens.*
import com.example.hatidaralapp.ui.theme.HatidAralTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HatidAralTheme { AppNav() } }
    }
}

@Composable
fun AppNav() {
    val nav = rememberNavController()
    val go: (String) -> Unit = { route -> nav.navigate(route) { launchSingleTop = true } }
    val back: () -> Unit = { nav.popBackStack() }

    NavHost(navController = nav, startDestination = Routes.SPLASH) {
        // Onboarding
        composable(Routes.SPLASH) {
            SplashScreen {
                nav.navigate(Routes.ROLE) { popUpTo(Routes.SPLASH) { inclusive = true } }
            }
        }
        composable(Routes.ROLE) {
            RoleSelectScreen(onBack = back, onContinue = { go(Routes.PERMISSIONS + "/$it") })
        }
        composable(Routes.PERMISSIONS + "/{role}") { entry ->
            val role = entry.arguments?.getString("role")
            PermissionsScreen(
                onBack = back,
                onAccept = {
                    val next = if (role == "Teacher") Routes.T_HOME else Routes.S_PAIR
                    nav.navigate(next) { popUpTo(Routes.ROLE) { inclusive = true } }
                }
            )
        }

        // Teacher
        composable(Routes.T_HOME) { TeacherDashboardScreen(go) }
        composable(Routes.T_CREATE) { CreateLessonScreen(go, back) }
        composable(Routes.T_QUIZ) { CreateQuizScreen(go, back) }
        composable(Routes.T_SEND) { TeacherSendScreen(go, back) }
        composable(Routes.T_SCAN) { ScanQrScreen(go, back) }
        composable(Routes.T_GRADES) { TeacherGradebookScreen(go) }

        // Student
        composable(Routes.S_PAIR) {
            StudentPairScreen(
                onAccept = { nav.navigate(Routes.S_HOME) { popUpTo(Routes.S_PAIR) { inclusive = true } } },
                onDecline = { nav.navigate(Routes.S_HOME) { popUpTo(Routes.S_PAIR) { inclusive = true } } }
            )
        }
        composable(Routes.S_HOME) { StudentLessonsScreen(go) }
        composable(Routes.S_SUBMIT) { SubmissionScreen(go, back) }
        composable(Routes.S_SEND) { StudentSendScreen(go, back) }
        composable(Routes.S_QR) { ShowQrScreen(go, back) }
        composable(Routes.S_GRADES) { StudentGradebookScreen(go) }
    }
}