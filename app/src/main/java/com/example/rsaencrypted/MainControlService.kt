package com.example.rsaencrypted

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rsaencrypted.encrypted.EncryptedLogsViewModel

class MainControlService : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHost = rememberNavController()
            val encryptedViewModel = EncryptedLogsViewModel()

            NavHost(navController = navHost, startDestination = ScreensInfo.correspondence) {
                composable(ScreensInfo.correspondence) {
                    Correspondence(navHost, encryptedViewModel)
                }

                composable(ScreensInfo.logScreen) {
                    LogScreen(encryptedViewModel)
                }
            }
        }
    }
}
