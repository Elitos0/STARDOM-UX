package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.Localization
import com.example.ui.theme.IbmPlexMono
import com.example.ui.theme.SpaceGrotesk
import com.example.ui.theme.StardomColors
import com.example.ui.theme.StardomDimensions

@Composable
fun StardomHeader(
  connected: Boolean,
  language: AppLanguage,
  onProfileClick: () -> Unit,
  onSettingsClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(StardomDimensions.TopBarHeight),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    HeaderButton(
      onClick = onProfileClick,
      testTag = "account_button"
    ) {
      Icon(
        imageVector = Icons.Outlined.Person,
        contentDescription = "Profile",
        tint = StardomColors.TextSecondary,
        modifier = Modifier.size(18.dp)
      )
    }

    Column(
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(3.5.dp)
            .background(StardomColors.BorderStrong)
        )

        Spacer(Modifier.width(8.dp))

        Text(
          text = "S T A R D O M",
          color = StardomColors.TextPrimary,
          fontFamily = SpaceGrotesk,
          fontWeight = FontWeight.Medium,
          fontSize = 17.sp,
          letterSpacing = 2.5.sp
        )

        Spacer(Modifier.width(8.dp))

        Box(
          modifier = Modifier
            .size(3.5.dp)
            .background(StardomColors.BorderStrong)
        )
      }

      Spacer(Modifier.height(5.dp))

      Text(
        text = if (connected) Localization.isSecured(language) else Localization.isOffline(language),
        color = StardomColors.TextSecondary,
        fontFamily = IbmPlexMono,
        fontSize = 9.sp,
        letterSpacing = 2.sp
      )
    }

    HeaderButton(
      onClick = onSettingsClick,
      testTag = "settings_button"
    ) {
      Icon(
        imageVector = Icons.Outlined.Settings,
        contentDescription = "Settings",
        tint = StardomColors.TextSecondary,
        modifier = Modifier.size(18.dp)
      )
    }
  }
}

@Composable
private fun HeaderButton(
  onClick: () -> Unit,
  testTag: String,
  content: @Composable BoxScope.() -> Unit
) {
  Box(
    modifier = Modifier
      .size(StardomDimensions.HeaderButtonSize)
      .background(StardomColors.Panel)
      .border(
        width = 1.dp,
        color = StardomColors.Border
      )
      .testTag(testTag)
      .clickable {
        onClick()
      },
    contentAlignment = Alignment.Center,
    content = content
  )
}
