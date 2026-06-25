package com.moes_code.moes_gym_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.moes_code.moes_gym_app.data.database.GymDatabase
import com.moes_code.moes_gym_app.data.repository.GymRepository
import com.moes_code.moes_gym_app.ui.navigation.GymNavHost
import com.moes_code.moes_gym_app.ui.theme.GYMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = GymDatabase.getInstance(this)
        val repository = GymRepository(database)

        enableEdgeToEdge()
        setContent {
            GYMTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GymNavHost(repository = repository)
                }
            }
        }
    }
}
