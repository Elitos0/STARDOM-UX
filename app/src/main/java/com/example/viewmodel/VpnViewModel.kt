package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AccountProfile
import com.example.model.ConnectionMode
import com.example.model.DnsProvider
import com.example.model.StarServerNode
import com.example.model.TelemetryState
import com.example.model.VpnProtocol
import com.example.model.VpnState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class VpnViewModel : ViewModel() {

  private val allServers = listOf(
    StarServerNode(
      id = "polaris_tokyo",
      starName = "POLARIS",
      constellation = "Ursa Minor",
      city = "Tokyo",
      countryCode = "JP",
      coordinates = "RA 02h 31m // DEC +89°15'",
      basePingMs = 18,
      loadPercent = 28,
      ipAddress = "194.26.29.14",
      cipher = "ChaCha20-Poly1305"
    ),
    StarServerNode(
      id = "sirius_newyork",
      starName = "SIRIUS",
      constellation = "Canis Major",
      city = "New York",
      countryCode = "US",
      coordinates = "RA 06h 45m // DEC -16°42'",
      basePingMs = 38,
      loadPercent = 42,
      ipAddress = "185.220.101.42",
      cipher = "ChaCha20-Poly1305"
    ),
    StarServerNode(
      id = "vega_frankfurt",
      starName = "VEGA",
      constellation = "Lyra",
      city = "Frankfurt",
      countryCode = "DE",
      coordinates = "RA 18h 36m // DEC +38°47'",
      basePingMs = 26,
      loadPercent = 35,
      ipAddress = "146.70.118.89",
      cipher = "AES-256-GCM"
    ),
    StarServerNode(
      id = "arcturus_london",
      starName = "ARCTURUS",
      constellation = "Boötes",
      city = "London",
      countryCode = "GB",
      coordinates = "RA 14h 15m // DEC +19°10'",
      basePingMs = 32,
      loadPercent = 48,
      ipAddress = "89.187.160.134",
      cipher = "ChaCha20-Poly1305"
    ),
    StarServerNode(
      id = "betelgeuse_singapore",
      starName = "BETELGEUSE",
      constellation = "Orion",
      city = "Singapore",
      countryCode = "SG",
      coordinates = "RA 05h 55m // DEC +07°24'",
      basePingMs = 45,
      loadPercent = 22,
      ipAddress = "103.252.119.50",
      cipher = "ChaCha20-Poly1305"
    ),
    StarServerNode(
      id = "rigel_amsterdam",
      starName = "RIGEL",
      constellation = "Orion",
      city = "Amsterdam",
      countryCode = "NL",
      coordinates = "RA 05h 14m // DEC -08°12'",
      basePingMs = 29,
      loadPercent = 54,
      ipAddress = "193.189.100.12",
      cipher = "AES-256-GCM"
    ),
    StarServerNode(
      id = "capella_stockholm",
      starName = "CAPELLA",
      constellation = "Auriga",
      city = "Stockholm",
      countryCode = "SE",
      coordinates = "RA 05h 16m // DEC +45°59'",
      basePingMs = 34,
      loadPercent = 19,
      ipAddress = "194.38.20.76",
      cipher = "ChaCha20-Poly1305"
    ),
    StarServerNode(
      id = "aldebaran_sydney",
      starName = "ALDEBARAN",
      constellation = "Taurus",
      city = "Sydney",
      countryCode = "AU",
      coordinates = "RA 04h 35m // DEC +16°30'",
      basePingMs = 94,
      loadPercent = 31,
      ipAddress = "139.99.144.205",
      cipher = "ChaCha20-Poly1305"
    ),
    StarServerNode(
      id = "deneb_zurich",
      starName = "DENEB",
      constellation = "Cygnus",
      city = "Zurich",
      countryCode = "CH",
      coordinates = "RA 20h 41m // DEC +45°16'",
      basePingMs = 27,
      loadPercent = 25,
      ipAddress = "179.43.144.12",
      cipher = "AES-256-GCM"
    ),
    StarServerNode(
      id = "antares_seoul",
      starName = "ANTARES",
      constellation = "Scorpius",
      city = "Seoul",
      countryCode = "KR",
      coordinates = "RA 16h 29m // DEC -26°25'",
      basePingMs = 41,
      loadPercent = 37,
      ipAddress = "118.217.202.99",
      cipher = "ChaCha20-Poly1305"
    )
  )

  private val _servers = MutableStateFlow(allServers)
  val servers: StateFlow<List<StarServerNode>> = _servers.asStateFlow()

  private val _activeServer = MutableStateFlow(allServers.first())
  val activeServer: StateFlow<StarServerNode> = _activeServer.asStateFlow()

  private val _vpnState = MutableStateFlow(VpnState.DISCONNECTED)
  val vpnState: StateFlow<VpnState> = _vpnState.asStateFlow()

  private val _connectionMode = MutableStateFlow(ConnectionMode.AUTO)
  val connectionMode: StateFlow<ConnectionMode> = _connectionMode.asStateFlow()

  private val _selectedProtocol = MutableStateFlow(VpnProtocol.WIREGUARD)
  val selectedProtocol: StateFlow<VpnProtocol> = _selectedProtocol.asStateFlow()

  private val _selectedDns = MutableStateFlow(DnsProvider.STARDOM_ZERO_KNOWLEDGE)
  val selectedDns: StateFlow<DnsProvider> = _selectedDns.asStateFlow()

  private val _selectedLanguage = MutableStateFlow(com.example.model.AppLanguage.RU)
  val selectedLanguage: StateFlow<com.example.model.AppLanguage> = _selectedLanguage.asStateFlow()

  private val _accountProfile = MutableStateFlow(AccountProfile())
  val accountProfile: StateFlow<AccountProfile> = _accountProfile.asStateFlow()

  private val _telemetryState = MutableStateFlow(TelemetryState())
  val telemetryState: StateFlow<TelemetryState> = _telemetryState.asStateFlow()

  private var telemetryJob: Job? = null

  fun toggleConnection() {
    when (_vpnState.value) {
      VpnState.DISCONNECTED -> {
        initiateConnection()
      }
      VpnState.SECURED -> {
        disconnect()
      }
      else -> {
        // In intermediate state, cancel and reset
        _vpnState.value = VpnState.DISCONNECTED
        stopTelemetry()
      }
    }
  }

  private fun initiateConnection() {
    viewModelScope.launch {
      _vpnState.value = VpnState.RESOLVING_STAR_ROUTE
      delay(650)

      // If auto mode, find optimal server with lowest ping
      if (_connectionMode.value == ConnectionMode.AUTO) {
        val optimal = allServers.minByOrNull { it.basePingMs } ?: allServers.first()
        _activeServer.value = optimal
      }

      _vpnState.value = VpnState.HANDSHAKING_CIPHER
      delay(750)

      _vpnState.value = VpnState.AUTHENTICATING_NODE
      delay(600)

      _vpnState.value = VpnState.SECURED
      _telemetryState.update {
        it.copy(
          currentPingMs = _activeServer.value.basePingMs,
          assignedVirtualIp = _activeServer.value.ipAddress
        )
      }
      startTelemetry()
    }
  }

  private fun disconnect() {
    viewModelScope.launch {
      _vpnState.value = VpnState.DISCONNECTING
      delay(450)
      stopTelemetry()
      _vpnState.value = VpnState.DISCONNECTED
      _telemetryState.update {
        it.copy(
          downloadSpeedMbps = 0f,
          uploadSpeedMbps = 0f
        )
      }
    }
  }

  private fun startTelemetry() {
    telemetryJob?.cancel()
    telemetryJob = viewModelScope.launch {
      while (isActive && _vpnState.value.isConnected) {
        delay(1000)
        _telemetryState.update { curr ->
          val dlVariation = Random.nextDouble(42.0, 118.0).toFloat()
          val ulVariation = Random.nextDouble(16.0, 48.0).toFloat()
          val addedDlMb = (dlVariation / 8.0)
          val addedUlMb = (ulVariation / 8.0)

          curr.copy(
            downloadSpeedMbps = dlVariation,
            uploadSpeedMbps = ulVariation,
            totalDownloadedMb = curr.totalDownloadedMb + addedDlMb,
            totalUploadedMb = curr.totalUploadedMb + addedUlMb,
            sessionDurationSeconds = curr.sessionDurationSeconds + 1,
            currentPingMs = (_activeServer.value.basePingMs + Random.nextInt(-3, 4)).coerceAtLeast(8)
          )
        }
      }
    }
  }

  private fun stopTelemetry() {
    telemetryJob?.cancel()
    telemetryJob = null
  }

  fun setConnectionMode(mode: ConnectionMode) {
    _connectionMode.value = mode
    if (mode == ConnectionMode.AUTO) {
      val optimal = allServers.minByOrNull { it.basePingMs } ?: allServers.first()
      _activeServer.value = optimal
      if (_vpnState.value.isConnected) {
        _telemetryState.update { it.copy(assignedVirtualIp = optimal.ipAddress) }
      }
    }
  }

  fun selectServer(server: StarServerNode) {
    _activeServer.value = server
    _connectionMode.value = ConnectionMode.MANUAL
    if (_vpnState.value.isConnected) {
      _telemetryState.update {
        it.copy(
          assignedVirtualIp = server.ipAddress,
          currentPingMs = server.basePingMs
        )
      }
    }
  }

  fun setProtocol(protocol: VpnProtocol) {
    _selectedProtocol.value = protocol
  }

  fun setDns(dns: DnsProvider) {
    _selectedDns.value = dns
  }

  fun setLanguage(language: com.example.model.AppLanguage) {
    _selectedLanguage.value = language
  }

  override fun onCleared() {
    super.onCleared()
    stopTelemetry()
  }
}
