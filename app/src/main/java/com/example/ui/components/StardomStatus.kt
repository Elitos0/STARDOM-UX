package com.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.VpnState
import com.example.ui.theme.IbmPlexMono
import com.example.ui.theme.SpaceGrotesk
import com.example.ui.theme.StardomColors

@Composable
fun StardomStatus(
  vpnState: VpnState,
  modifier: Modifier = Modifier,
  language: AppLanguage = AppLanguage.RU
) {
  val connected = vpnState.isConnected
  val connecting = vpnState.isConnecting

  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = if (language == AppLanguage.RU) "ТЕКУЩИЙ СТАТУС" else "CURRENT STATUS",
      color = StardomColors.TextSecondary,
      fontFamily = IbmPlexMono,
      fontSize = 9.sp,
      letterSpacing = 2.sp
    )

    Spacer(Modifier.height(6.dp))

    val statusText = when {
      connecting -> if (language == AppLanguage.RU) "ПОДКЛЮЧЕНИЕ" else "ASCENDING"
      connected -> if (language == AppLanguage.RU) "НА ОРБИТЕ" else "IN ORBIT"
      else -> if (language == AppLanguage.RU) "ОТКЛЮЧЕНО" else "DE-ORBITED"
    }

    Text(
      text = statusText,
      color = StardomColors.TextPrimary,
      fontFamily = SpaceGrotesk,
      fontWeight = FontWeight.Medium,
      fontSize = 23.sp,
      letterSpacing = 3.5.sp
    )
  }
}
