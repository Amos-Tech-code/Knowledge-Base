package com.amos_tech_code.knowledgebase

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amos_tech_code.knowledgebase.notifications.ReminderWorker
import com.amos_tech_code.knowledgebase.ui.KnowledgeBaseApp
import com.amos_tech_code.knowledgebase.ui.LoginScreen
import com.amos_tech_code.knowledgebase.ui.theme.KnowledgeBaseTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Track the daily visit for the Play Store 14-day requirement
        DailyTracker.recordDailyVisit(this)

        // Schedule daily reminders
        ReminderWorker.scheduleDailyReminder(this)

        enableEdgeToEdge()
        setContent {
            KnowledgeBaseTheme {
                val navController = rememberNavController()
                val currentUser = FirebaseAuth.getInstance().currentUser
                val context = LocalContext.current

                // Request notification permission for Android 13+
                val launcher =
                    rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestPermission(),
                    ) { isGranted ->
                        // Handle permission result if needed
                    }

                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS,
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (currentUser != null) "main" else "login",
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = {
                                    DailyTracker.recordDailyVisit(context)
                                    navController.navigate("main") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                            )
                        }
                        composable("main") {
                            KnowledgeBaseApp()
                        }
                    }
                }
            }
        }
    }
}
