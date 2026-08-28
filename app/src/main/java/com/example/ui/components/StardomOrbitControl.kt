package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.VpnState
import com.example.ui.theme.IbmPlexMono
import com.example.ui.theme.StardomColors
import com.example.ui.theme.StardomDimensions
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StardomOrbitControl(
  vpnState: VpnState,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  language: AppLanguage = AppLanguage.RU
) {
  val connected = vpnState.isConnected
  val connecting = vpnState.isConnecting

  // Precision orbital tick rotation
  val tickRotation = remember { Animatable(0f) }

  LaunchedEffect(vpnState) {
    if (connected) {
      val current = tickRotation.value
      val remainder = current % 45f
      val target = current + (45f - remainder)
      tickRotation.animateTo(
        targetValue = target,
        animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
      )
    } else {
      while (true) {
        val duration = if (connecting) 4000 else 28000
        val current = tickRotation.value
        tickRotation.animateTo(
          targetValue = current + 360f,
          animationSpec = tween(durationMillis = duration, easing = LinearEasing)
        )
      }
    }
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .aspectRatio(1f),
    contentAlignment = Alignment.Center
  ) {
    Canvas(
      modifier = Modifier.fillMaxSize()
    ) {
      val center = center
      val outerRadius = size.minDimension * 0.43f
      val secondaryRadius = size.minDimension * 0.35f
      val diamondRadius = size.minDimension * 0.36f

      /*
       * OUTER ORBIT (pure 1px stroke)
       */
      drawCircle(
        color = StardomColors.BorderStrong,
        radius = outerRadius,
        center = center,
        style = Stroke(width = 1f)
      )

      /*
       * SECONDARY ORBIT (pure 1px stroke)
       */
      drawCircle(
        color = StardomColors.Border,
        radius = secondaryRadius,
        center = center,
        style = Stroke(width = 1f)
      )

      /*
       * DIAMOND (pure 1px stroke)
       */
      val top = Offset(center.x, center.y - diamondRadius)
      val right = Offset(center.x + diamondRadius, center.y)
      val bottom = Offset(center.x, center.y + diamondRadius)
      val left = Offset(center.x - diamondRadius, center.y)

      val diamondPath = Path().apply {
        moveTo(top.x, top.y)
        lineTo(right.x, right.y)
        lineTo(bottom.x, bottom.y)
        lineTo(left.x, left.y)
        close()
      }

      drawPath(
        path = diamondPath,
        color = if (connected) StardomColors.BorderStrong else StardomColors.Border,
        style = Stroke(width = 1f)
      )

      // Tiny node markers at diamond vertices
      val diamondNodes = listOf(top, right, bottom, left)
      for (node in diamondNodes) {
        drawCircle(
          color = if (connected) StardomColors.TextPrimary else StardomColors.BorderStrong,
          radius = 2.dp.toPx(),
          center = node
        )
      }

      /*
       * CARDINAL SQUARE NODES ON OUTER ORBIT (top, right, bottom, left)
       */
      val sqSize = 3.5.dp.toPx()
      val cardinalOffsets = listOf(
        Offset(center.x - sqSize / 2, center.y - outerRadius - sqSize / 2),
        Offset(center.x + outerRadius - sqSize / 2, center.y - sqSize / 2),
        Offset(center.x - sqSize / 2, center.y + outerRadius - sqSize / 2),
        Offset(center.x - outerRadius - sqSize / 2, center.y - sqSize / 2)
      )

      for (offset in cardinalOffsets) {
        drawRect(
          color = if (connected) StardomColors.TextPrimary else StardomColors.BorderStrong,
          topLeft = offset,
          size = Size(sqSize, sqSize)
        )
      }

      /*
       * ROTATING ORBIT TICKS (pure 1px lines)
       */
      withTransform({
        rotate(degrees = tickRotation.value, pivot = center)
      }) {
        repeat(8) { index ->
          val degrees = (index * 45) - 90
          val radians = Math.toRadians(degrees.toDouble())

          val r1 = outerRadius - 6.dp.toPx()
          val r2 = outerRadius + 4.dp.toPx()

          val x1 = center.x + cos(radians).toFloat() * r1
          val y1 = center.y + sin(radians).toFloat() * r1

          val x2 = center.x + cos(radians).toFloat() * r2
          val y2 = center.y + sin(radians).toFloat() * r2

          drawLine(
            color = if (connected) StardomColors.TextPrimary else StardomColors.BorderStrong,
            start = Offset(x1, y1),
            end = Offset(x2, y2),
            strokeWidth = 1f
          )
        }
      }
    }

    /*
     * PHYSICAL CENTRAL BUTTON (square, 140dp, 1px border)
     */
    Column(
      modifier = Modifier
        .size(StardomDimensions.MainControlSize)
        .background(StardomColors.Panel)
        .border(
          width = 1.dp,
          color = if (connected) StardomColors.TextPrimary else StardomColors.BorderStrong
        )
        .testTag("vpn_power_button")
        .clickable {
          onClick()
        },
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Icon(
        imageVector = Icons.Outlined.PowerSettingsNew,
        contentDescription = "Power toggle",
        tint = if (connected) StardomColors.TextPrimary else StardomColors.TextSecondary,
        modifier = Modifier.size(20.dp)
      )

      Spacer(Modifier.height(20.dp))

      val label = when {
        connecting -> if (language == AppLanguage.RU) "ИНИЦИАЛИЗАЦИЯ" else "INITIALIZING"
        connected -> if (language == AppLanguage.RU) "ОТКЛЮЧИТЬ" else "TERMINATE"
        else -> if (language == AppLanguage.RU) "ПОДКЛЮЧИТЬ" else "INITIALIZE"
      }

      Text(
        text = label,
        color = StardomColors.TextPrimary,
        fontFamily = IbmPlexMono,
        fontWeight = FontWeight.Normal,
        fontSize = 11.5.sp,
        letterSpacing = 2.sp
      )
    }
  }
}
