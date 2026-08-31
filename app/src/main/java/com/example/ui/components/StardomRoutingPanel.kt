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
import androidx.compose.foundation.layout.fillMaxHeight

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

    Spacer(Modifier.height(8.dp))

    /*
     * AUTO / MANUAL SELECTORS (50 / 50)
     */
    /*
     * AUTO / MANUAL — единый общий контейнер.
     *
     * Было:
     * [ AUTO ]   [ MANUAL ]
     *
     * Стало:
     * [ AUTO | MANUAL ]
     */
    Row(
      modifier = Modifier
        .fillMaxWidth()

        // ВЫСОТА всей общей плашки AUTO / MANUAL.
        // ↑ увеличить -> плашка станет выше.
        // ↓ уменьшить -> компактнее.
        .height(60.dp)

        // ОБЩАЯ внешняя рамка сразу вокруг AUTO + MANUAL.
        .border(
          width = 1.dp,
          color = StardomColors.BorderStrong
        )
    ) {

      RoutingModeCell(
        title = "AUTO",
        subtitle = if (language == AppLanguage.RU)
          "НИЗКИЙ ПИНГ"
        else
          "LOWEST LATENCY",
        selected = connectionMode == ConnectionMode.AUTO,
        testTag = "mode_tab_auto",
        modifier = Modifier.weight(1f),
        onClick = {
          onRoutingModeChange(ConnectionMode.AUTO)
        }
      )

      /*
       * ЦЕНТРАЛЬНЫЙ РАЗДЕЛИТЕЛЬ AUTO | MANUAL
       *
       * width = толщина линии.
       */
      Box(
        modifier = Modifier
          .fillMaxHeight()
          .width(1.dp)
          .background(StardomColors.Border)
      )

      RoutingModeCell(
        title = "MANUAL",
        subtitle = if (language == AppLanguage.RU)
          "ВЫБОР УЗЛА"
        else
          "SELECT NODE",
        selected = connectionMode == ConnectionMode.MANUAL,
        testTag = "mode_tab_manual",
        modifier = Modifier.weight(1f),
        onClick = {
          onRoutingModeChange(ConnectionMode.MANUAL)
        }
      )
    }

    Spacer(Modifier.height(10.dp))

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

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(StardomColors.Border)
    )

    Spacer(Modifier.height(9.dp))

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
          lineHeight = 18.sp,
          letterSpacing = 1.2.sp
        )

        Spacer(Modifier.height(3.dp))

        Text(
          text = "${activeServer.city.uppercase()} / ${activeServer.countryCode} • ${activeServer.constellation}",
          color = StardomColors.TextSecondary,
          fontFamily = IbmPlexMono,
          fontSize = 9.sp,
          lineHeight = 10.sp,
          letterSpacing = 1.2.sp
        )
      }

      Spacer(Modifier.width(8.dp))

      Box(
        modifier = Modifier
          .size(36.dp)
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
      .fillMaxHeight()

      /*
       * Не ставим здесь border().
       *
       * Общая рамка теперь находится снаружи сразу
       * вокруг AUTO + MANUAL.
       */

      /*
       * Очень слабое выделение выбранной половины.
       *
       * Если вообще не хочешь различия по фону,
       * замени всё на:
       *
       * .background(StardomColors.Panel)
       */
      .background(
        if (selected)
          StardomColors.PanelSelected
        else
          StardomColors.Panel
      )

      .testTag(testTag)

      .clickable {
        onClick()
      }

      /*
       * ОТСТУП ОТ ЛЕВОГО КРАЯ ПЛАШКИ ДО КВАДРАТИКА.
       *
       * Раньше было: 12.dp
       * Сейчас:      24.dp
       *
       * Хочешь квадратик ближе к краю -> уменьшай.
       * Хочешь дальше от края -> увеличивай.
       */
      .padding(
        start = 20.dp,
        end = 10.dp
      ),

    verticalAlignment = Alignment.CenterVertically
  ) {

    /*
     * ВНЕШНИЙ КВАДРАТ — индикатор выбора.
     */
    Box(
      modifier = Modifier

        /*
         * РАЗМЕР внешнего квадрата.
         *
         * Было: 12.dp
         * Стало: 24.dp
         *
         * 20.dp — немного аккуратнее.
         * 24.dp — ровно примерно ×2.
         */
        .size(24.dp)

        /*
         * Толщина рамки большого квадрата.
         *
         * Сейчас 1.dp.
         * Если хочется чуть массивнее -> 1.5.dp.
         */
        .border(
          width = 1.dp,
          color = if (selected)
            StardomColors.Selected
          else
            StardomColors.TextMuted
        ),

      contentAlignment = Alignment.Center
    ) {

      if (selected) {

        /*
         * ВНУТРЕННИЙ заполненный квадратик.
         *
         * Было:
         *
         * outer = 12.dp
         * inner = 4.dp
         *
         * Свободное пространство:
         * (12 - 4) / 2 = 4.dp с каждой стороны.
         *
         *
         * Сейчас:
         *
         * outer = 24.dp
         * inner = 8.dp
         *
         * Свободное пространство:
         * (24 - 8) / 2 = 8.dp.
         *
         * То есть внутренний "воздух" увеличился
         * ровно примерно в 2 раза.
         */
        Box(
          modifier = Modifier
            .size(16.dp)
            .background(
              StardomColors.Selected
            )
        )
      }
    }


    /*
     * РАССТОЯНИЕ МЕЖДУ КВАДРАТОМ И ТЕКСТОМ.
     *
     * Это НЕ отступ от левого края.
     *
     * 8.dp  -> текст ближе к квадрату.
     * 12.dp -> больше воздуха.
     * 16.dp -> уже довольно широко.
     */
    Spacer(
      Modifier.width(15.dp)
    )


    /*
     * AUTO
     * LOWEST LATENCY
     */
    Column(
      verticalArrangement = Arrangement.spacedBy(

        /*
         * ВЕРТИКАЛЬНОЕ расстояние:
         *
         * AUTO
         * ↓
         * LOWEST LATENCY
         *
         * 0.dp -> практически вплотную.
         * 2.dp -> хороший плотный вариант.
         * 4.dp+ -> заметно разъезжается.
         */
        2.dp
      )
    ) {

      Text(
        text = title,

        color = if (selected)
          StardomColors.TextPrimary
        else
          StardomColors.TextSecondary,

        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,

        /*
         * Размер AUTO / MANUAL.
         */
        fontSize = 13.sp,

        /*
         * Высота строки AUTO / MANUAL.
         *
         * Если визуально слишком много воздуха
         * над/под буквами — уменьшать именно это.
         */
        lineHeight = 14.sp,

        letterSpacing = 1.2.sp
      )

      Text(
        text = subtitle,

        color = StardomColors.TextSecondary,

        fontFamily = IbmPlexMono,

        /*
         * Размер LOWEST LATENCY / SELECT NODE.
         */
        fontSize = 8.sp,

        /*
         * Высота второй строки.
         */
        lineHeight = 9.sp,

        letterSpacing = 1.sp
      )
    }
  }
}
