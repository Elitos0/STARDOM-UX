package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.DnsProvider
import com.example.model.Localization
import com.example.model.VpnProtocol
import com.example.ui.theme.IbmPlexMono
import com.example.ui.theme.SpaceGrotesk
import com.example.ui.theme.StardomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StardomSettingsSheet(
  selectedProtocol: VpnProtocol,
  onSelectProtocol: (VpnProtocol) -> Unit,
  selectedDns: DnsProvider,
  onSelectDns: (DnsProvider) -> Unit,
  selectedLanguage: AppLanguage,
  onSelectLanguage: (AppLanguage) -> Unit,
  sheetState: SheetState,
  onDismiss: () -> Unit
) {
  var killSwitchEnabled by remember { mutableStateOf(true) }
  var dnsLeakGuardEnabled by remember { mutableStateOf(true) }
  var obfuscationEnabled by remember { mutableStateOf(true) }
  var autoConnectOnWifi by remember { mutableStateOf(false) }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = StardomColors.Background,
    scrimColor = StardomColors.Background.copy(alpha = 0.88f),
    dragHandle = {
      Box(
        modifier = Modifier
          .padding(vertical = 10.dp)
          .width(36.dp)
          .height(1.dp)
          .background(StardomColors.BorderStrong)
      )
    }
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.85f)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 6.dp)
    ) {
      // Header
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column {
          Text(
            text = Localization.settingsTitle(selectedLanguage),
            color = StardomColors.TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = SpaceGrotesk,
            letterSpacing = 1.2.sp
          )
          Spacer(Modifier.height(3.dp))
          Text(
            text = Localization.settingsSubtitle(selectedLanguage),
            color = StardomColors.TextSecondary,
            fontSize = 9.sp,
            fontFamily = IbmPlexMono,
            letterSpacing = 1.sp
          )
        }

        Box(
          modifier = Modifier
            .background(StardomColors.Panel)
            .border(1.dp, StardomColors.Border)
            .clickable { onDismiss() }
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
          Text(
            text = Localization.doneBtn(selectedLanguage),
            color = StardomColors.TextSecondary,
            fontSize = 9.sp,
            fontFamily = IbmPlexMono,
            letterSpacing = 1.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Section: Language Switcher
      SettingsSectionHeader(title = Localization.languageSection(selectedLanguage))
      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        AppLanguage.entries.forEach { lang ->
          val isSelected = lang == selectedLanguage
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .weight(1f)
              .testTag("language_option_${lang.code}")
              .background(if (isSelected) StardomColors.PanelSelected else StardomColors.Panel)
              .border(
                1.dp,
                if (isSelected) StardomColors.BorderStrong else StardomColors.BorderFaint
              )
              .clickable { onSelectLanguage(lang) }
              .padding(vertical = 12.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(10.dp)
                  .border(
                    1.dp,
                    if (isSelected) StardomColors.Selected else StardomColors.TextMuted
                  ),
                contentAlignment = Alignment.Center
              ) {
                if (isSelected) {
                  Box(
                    modifier = Modifier
                      .size(4.dp)
                      .background(StardomColors.Selected)
                  )
                }
              }
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "${lang.title} [${lang.code}]",
                color = if (isSelected) StardomColors.TextPrimary else StardomColors.TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = SpaceGrotesk,
                letterSpacing = 1.sp
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Section: Protocol Engine
      SettingsSectionHeader(title = Localization.protocolSection(selectedLanguage))
      Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        VpnProtocol.entries.forEach { proto ->
          val isSelected = proto == selectedProtocol
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .testTag("proto_option_${proto.name}")
              .background(if (isSelected) StardomColors.PanelSelected else StardomColors.Panel)
              .border(
                1.dp,
                if (isSelected) StardomColors.BorderStrong else StardomColors.BorderFaint
              )
              .clickable { onSelectProtocol(proto) }
              .padding(12.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(10.dp)
                    .border(
                      1.dp,
                      if (isSelected) StardomColors.Selected else StardomColors.TextMuted
                    ),
                  contentAlignment = Alignment.Center
                ) {
                  if (isSelected) {
                    Box(
                      modifier = Modifier
                        .size(4.dp)
                        .background(StardomColors.Selected)
                    )
                  }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = proto.displayName,
                    color = if (isSelected) StardomColors.TextPrimary else StardomColors.TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = SpaceGrotesk
                  )
                  Spacer(Modifier.height(2.dp))
                  Text(
                    text = "CIPHER: ${proto.cipher} • PORT: ${proto.port}",
                    color = StardomColors.TextMuted,
                    fontSize = 9.sp,
                    fontFamily = IbmPlexMono
                  )
                }
              }

              if (isSelected) {
                Text(
                  text = Localization.activeStatus(selectedLanguage),
                  color = StardomColors.TextPrimary,
                  fontSize = 9.sp,
                  fontFamily = IbmPlexMono,
                  letterSpacing = 1.sp
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Section: DNS Resolver
      SettingsSectionHeader(title = Localization.dnsSection(selectedLanguage))
      Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        DnsProvider.entries.forEach { dns ->
          val isSelected = dns == selectedDns
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .testTag("dns_option_${dns.name}")
              .background(if (isSelected) StardomColors.PanelSelected else StardomColors.Panel)
              .border(
                1.dp,
                if (isSelected) StardomColors.BorderStrong else StardomColors.BorderFaint
              )
              .clickable { onSelectDns(dns) }
              .padding(12.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(10.dp)
                    .border(
                      1.dp,
                      if (isSelected) StardomColors.Selected else StardomColors.TextMuted
                    ),
                  contentAlignment = Alignment.Center
                ) {
                  if (isSelected) {
                    Box(
                      modifier = Modifier
                        .size(4.dp)
                        .background(StardomColors.Selected)
                    )
                  }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = dns.displayName,
                    color = if (isSelected) StardomColors.TextPrimary else StardomColors.TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = SpaceGrotesk
                  )
                  Spacer(Modifier.height(2.dp))
                  Text(
                    text = "GATEWAY: ${dns.address}",
                    color = StardomColors.TextMuted,
                    fontSize = 9.sp,
                    fontFamily = IbmPlexMono
                  )
                }
              }

              if (isSelected) {
                Text(
                  text = Localization.lockedStatus(selectedLanguage),
                  color = StardomColors.TextPrimary,
                  fontSize = 9.sp,
                  fontFamily = IbmPlexMono,
                  letterSpacing = 1.sp
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Section: Security Toggles
      SettingsSectionHeader(title = Localization.securitySection(selectedLanguage))

      StrictGeometricToggleRow(
        title = Localization.killSwitchTitle(selectedLanguage),
        subtitle = Localization.killSwitchDesc(selectedLanguage),
        isEnabled = killSwitchEnabled,
        testTag = "toggle_kill_switch",
        onToggle = { killSwitchEnabled = !killSwitchEnabled }
      )

      Spacer(modifier = Modifier.height(6.dp))

      StrictGeometricToggleRow(
        title = Localization.dnsGuardTitle(selectedLanguage),
        subtitle = Localization.dnsGuardDesc(selectedLanguage),
        isEnabled = dnsLeakGuardEnabled,
        testTag = "toggle_dns_guard",
        onToggle = { dnsLeakGuardEnabled = !dnsLeakGuardEnabled }
      )

      Spacer(modifier = Modifier.height(6.dp))

      StrictGeometricToggleRow(
        title = Localization.obfuscationTitle(selectedLanguage),
        subtitle = Localization.obfuscationDesc(selectedLanguage),
        isEnabled = obfuscationEnabled,
        testTag = "toggle_obfuscation",
        onToggle = { obfuscationEnabled = !obfuscationEnabled }
      )

      Spacer(modifier = Modifier.height(6.dp))

      StrictGeometricToggleRow(
        title = Localization.autoWifiTitle(selectedLanguage),
        subtitle = Localization.autoWifiDesc(selectedLanguage),
        isEnabled = autoConnectOnWifi,
        testTag = "toggle_auto_wifi",
        onToggle = { autoConnectOnWifi = !autoConnectOnWifi }
      )

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun SettingsSectionHeader(title: String) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp)
  ) {
    Text(
      text = title,
      color = StardomColors.TextSecondary,
      fontSize = 9.sp,
      fontWeight = FontWeight.Medium,
      fontFamily = IbmPlexMono,
      letterSpacing = 1.5.sp
    )
  }
}

@Composable
private fun StrictGeometricToggleRow(
  title: String,
  subtitle: String,
  isEnabled: Boolean,
  testTag: String,
  onToggle: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .testTag(testTag)
      .background(StardomColors.Panel)
      .border(
        1.dp,
        if (isEnabled) StardomColors.BorderStrong else StardomColors.BorderFaint
      )
      .clickable { onToggle() }
      .padding(12.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          color = if (isEnabled) StardomColors.TextPrimary else StardomColors.TextSecondary,
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium,
          fontFamily = SpaceGrotesk
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
          text = subtitle,
          color = StardomColors.TextMuted,
          fontSize = 9.sp,
          fontFamily = IbmPlexMono
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      // Strict geometric switch (34x18dp sharp square frame)
      Box(
        modifier = Modifier
          .width(34.dp)
          .height(18.dp)
          .background(StardomColors.Background)
          .border(
            1.dp,
            if (isEnabled) StardomColors.BorderStrong else StardomColors.BorderFaint
          )
          .padding(2.dp)
      ) {
        Box(
          modifier = Modifier
            .size(12.dp)
            .align(if (isEnabled) Alignment.CenterEnd else Alignment.CenterStart)
            .background(if (isEnabled) StardomColors.Selected else StardomColors.TextMuted)
        )
      }
    }
  }
}
