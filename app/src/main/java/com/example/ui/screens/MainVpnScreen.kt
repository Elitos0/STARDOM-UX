package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.ConnectionMode
import com.example.ui.components.StardomAccountDialog
import com.example.ui.components.StardomBackground
import com.example.ui.components.StardomHeader
import com.example.ui.components.StardomOrbitControl
import com.example.ui.components.StardomRoutingPanel
import com.example.ui.components.StardomServerSelectorSheet
import com.example.ui.components.StardomSettingsSheet
import com.example.ui.components.StardomStatus
import com.example.ui.theme.StardomColors
import com.example.ui.theme.StardomDimensions
import com.example.viewmodel.VpnViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainVpnScreen(
  viewModel: VpnViewModel,
  modifier: Modifier = Modifier
) {
  val vpnState by viewModel.vpnState.collectAsStateWithLifecycle()
  val connectionMode by viewModel.connectionMode.collectAsStateWithLifecycle()
  val activeServer by viewModel.activeServer.collectAsStateWithLifecycle()
  val servers by viewModel.servers.collectAsStateWithLifecycle()
  val selectedProtocol by viewModel.selectedProtocol.collectAsStateWithLifecycle()
  val selectedDns by viewModel.selectedDns.collectAsStateWithLifecycle()
  val selectedLanguage by viewModel.selectedLanguage.collectAsStateWithLifecycle()
  val accountProfile by viewModel.accountProfile.collectAsStateWithLifecycle()

  var showAccountDialog by remember { mutableStateOf(false) }
  var showSettingsSheet by remember { mutableStateOf(false) }
  var showServerSheet by remember { mutableStateOf(false) }

  val serverSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  val settingsSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(StardomColors.Background)
  ) {
    /*
     * BACKGROUND (Technical grid + subtle intersection crosses + refined constellations)
     */
    StardomBackground(
      vpnState = vpnState
    )

    /*
     * MAIN UI WITH ADAPTIVE VERTICAL CONTROL
     */
    BoxWithConstraints(
      modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.safeDrawing)
        .padding(horizontal = StardomDimensions.ScreenHorizontal)
    ) {
      val availableHeight = maxHeight

      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        StardomHeader(
          connected = vpnState.isConnected,
          language = selectedLanguage,
          onProfileClick = { showAccountDialog = true },
          onSettingsClick = { showSettingsSheet = true }
        )

        // Measured top vertical spacing between Header and Orbit Control
        val topSpacerWeight = if (availableHeight > 800.dp) 0.08f else 0.04f
        Spacer(Modifier.weight(topSpacerWeight))

        StardomOrbitControl(
          vpnState = vpnState,
          onClick = { viewModel.toggleConnection() },
          language = selectedLanguage,
          modifier = Modifier.fillMaxWidth(0.92f)
        )

        Spacer(Modifier.height(10.dp))

        StardomStatus(
          vpnState = vpnState,
          language = selectedLanguage
        )

        // Measured bottom vertical spacing between Status and Routing Panel
        val bottomSpacerWeight = if (availableHeight > 800.dp) 0.10f else 0.05f
        Spacer(Modifier.weight(bottomSpacerWeight))

        StardomRoutingPanel(
          connectionMode = connectionMode,
          activeServer = activeServer,
          onRoutingModeChange = { mode ->
            viewModel.setConnectionMode(mode)
            if (mode == ConnectionMode.MANUAL) {
              showServerSheet = true
            }
          },
          onNodeClick = { showServerSheet = true },
          language = selectedLanguage
        )

        Spacer(Modifier.height(12.dp))
      }
    }

    /*
     * MODAL DIALOGS & BOTTOM SHEETS
     */
    if (showAccountDialog) {
      StardomAccountDialog(
        profile = accountProfile,
        onDismiss = { showAccountDialog = false },
        language = selectedLanguage
      )
    }

    if (showSettingsSheet) {
      StardomSettingsSheet(
        selectedProtocol = selectedProtocol,
        onSelectProtocol = { viewModel.setProtocol(it) },
        selectedDns = selectedDns,
        onSelectDns = { viewModel.setDns(it) },
        selectedLanguage = selectedLanguage,
        onSelectLanguage = { viewModel.setLanguage(it) },
        sheetState = settingsSheetState,
        onDismiss = { showSettingsSheet = false }
      )
    }

    if (showServerSheet) {
      StardomServerSelectorSheet(
        servers = servers,
        selectedServer = activeServer,
        sheetState = serverSheetState,
        onDismiss = { showServerSheet = false },
        onSelectServer = { server ->
          viewModel.selectServer(server)
        },
        language = selectedLanguage
      )
    }
  }
}
