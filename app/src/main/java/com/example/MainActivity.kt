package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.ui.screens.MainVpnScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SpaceVoid
import com.example.viewmodel.VpnViewModel

class MainActivity : ComponentActivity() {
  private val vpnViewModel: VpnViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme(darkTheme = true) {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = SpaceVoid
        ) {
          MainVpnScreen(viewModel = vpnViewModel)
        }
      }
    }
  }
}

