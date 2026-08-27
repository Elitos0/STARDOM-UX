package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
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
import com.example.model.ConnectionMode
import com.example.model.StarServerNode
import com.example.ui.theme.IbmPlexMono
import com.example.ui.theme.SpaceGrotesk
import com.example.ui.theme.StardomColors
import com.example.ui.theme.StardomDimensions

@Composable
fun StardomRoutingPanel(
  connectionMode: ConnectionMode,
  activeServer: StarServerNode,
  onRoutingModeChange: (ConnectionMode) -> Unit,
  onNodeClick: () -> Unit,
  modifier: Modifier = Modifier,
  language: AppLanguage = AppLanguage.RU
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(StardomColors.Panel)
      .border(
        width = 1.dp,
        color = StardomColors.Border
      )
      .padding(
        horizontal = StardomDimensions.PanelPaddingHorizontal,
        vertical = StardomDimensions.PanelPaddingVertical
      )
  ) {
    /*
     * ROUTING SECTION HEADER
     */
    Text(
      text = if (language == AppLanguage.RU) "МАРШРУТИЗАЦИЯ" else "ROUTING",
      color = StardomColors.TextSecondary,
      fontFamily = IbmPlexMono,
      fontSize = 9.sp,
      letterSpacing = 2.sp
    )

    Spacer(Modifier.height(11.dp))

    /*
     * AUTO / MANUAL SELECTORS (50 / 50)
     */
    Row(
      modifier = Modifier.fillMaxWidth()
    ) {
      RoutingModeCell(
        title = "AUTO",
        subtitle = if (language == AppLanguage.RU) "НИЗКИЙ ПИНГ" else "LOWEST LATENCY",
        selected = connectionMode == ConnectionMode.AUTO,
        testTag = "mode_tab_auto",
        modifier = Modifier.weight(1f),
        onClick = {
          onRoutingModeChange(ConnectionMode.AUTO)
        }
      )

      Spacer(Modifier.width(8.dp))

      RoutingModeCell(
        title = "MANUAL",
        subtitle = if (language == AppLanguage.RU) "ВЫБОР УЗЛА" else "SELECT NODE",
        selected = connectionMode == ConnectionMode.MANUAL,
        testTag = "mode_tab_manual",
        modifier = Modifier.weight(1f),
        onClick = {
          onRoutingModeChange(ConnectionMode.MANUAL)
        }
      )
    }

    Spacer(Modifier.height(13.dp))

    /*
     * HAIRLINE DIVIDER (1px)
     */
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(StardomColors.Border)
    )

    Spacer(Modifier.height(13.dp))

    /*
     * ACTIVE NODE METADATA & LATENCY
     */
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = if (language == AppLanguage.RU) "АКТИВНЫЙ УЗЕЛ" else "ACTIVE NODE",
        color = StardomColors.TextSecondary,
        fontFamily = IbmPlexMono,
        fontSize = 9.sp,
        letterSpacing = 1.8.sp
      )

      Text(
        text = "${activeServer.basePingMs} MS",
        color = StardomColors.TextSecondary,
        fontFamily = IbmPlexMono,
        fontSize = 9.sp,
        letterSpacing = 1.5.sp
      )
    }

    Spacer(Modifier.height(8.dp))

    /*
     * ACTIVE NODE MAIN ROW & DROPDOWN
     */
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("active_server_card")
        .clickable {
          onNodeClick()
        },
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(
        modifier = Modifier.weight(1f)
      ) {
        val nodeTitle = if (connectionMode == ConnectionMode.AUTO) {
          if (language == AppLanguage.RU) "AUTO / ${activeServer.starName}" else "AUTO / ${activeServer.starName}"
        } else {
          "${activeServer.starName}: ${activeServer.city.uppercase()}"
        }

        Text(
          text = nodeTitle,
          color = StardomColors.TextPrimary,
          fontFamily = SpaceGrotesk,
          fontWeight = FontWeight.Medium,
          fontSize = 16.sp,
          letterSpacing = 1.2.sp
        )

        Spacer(Modifier.height(6.dp))

        Text(
          text = "${activeServer.city.uppercase()} / ${activeServer.countryCode} • ${activeServer.constellation}",
          color = StardomColors.TextSecondary,
          fontFamily = IbmPlexMono,
          fontSize = 9.sp,
          letterSpacing = 1.2.sp
        )
      }

      Spacer(Modifier.width(10.dp))

      Box(
        modifier = Modifier
          .size(40.dp)
          .border(
            width = 1.dp,
            color = StardomColors.Border
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Outlined.KeyboardArrowDown,
          contentDescription = "Select node",
          tint = StardomColors.TextSecondary,
          modifier = Modifier.size(18.dp)
        )
      }
    }
  }
}

@Composable
private fun RoutingModeCell(
  title: String,
  subtitle: String,
  selected: Boolean,
  testTag: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .height(56.dp)
      .background(
        if (selected) StardomColors.PanelSelected else StardomColors.Panel
      )
      .border(
        width = 1.dp,
        color = if (selected) StardomColors.BorderStrong else StardomColors.BorderFaint
      )
      .testTag(testTag)
      .clickable {
        onClick()
      }
      .padding(horizontal = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    /*
     * SQUARE INDICATOR
     */
    Box(
      modifier = Modifier
        .size(12.dp)
        .border(
          width = 1.dp,
          color = if (selected) StardomColors.Selected else StardomColors.TextMuted
        ),
      contentAlignment = Alignment.Center
    ) {
      if (selected) {
        Box(
          modifier = Modifier
            .size(4.dp)
            .background(StardomColors.Selected)
        )
      }
    }

    Spacer(Modifier.width(10.dp))

    Column {
      Text(
        text = title,
        color = if (selected) StardomColors.TextPrimary else StardomColors.TextSecondary,
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        letterSpacing = 1.2.sp
      )

      Spacer(Modifier.height(3.dp))

      Text(
        text = subtitle,
        color = StardomColors.TextSecondary,
        fontFamily = IbmPlexMono,
        fontSize = 8.sp,
        letterSpacing = 1.sp
      )
    }
  }
}
