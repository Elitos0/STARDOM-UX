package com.example.model

enum class VpnState {
  DISCONNECTED,
  RESOLVING_STAR_ROUTE,
  HANDSHAKING_CIPHER,
  AUTHENTICATING_NODE,
  SECURED,
  DISCONNECTING;

  val isConnected: Boolean get() = this == SECURED
  val isConnecting: Boolean get() = this == RESOLVING_STAR_ROUTE || this == HANDSHAKING_CIPHER || this == AUTHENTICATING_NODE
}

enum class ConnectionMode {
  AUTO,
  MANUAL
}

enum class AppLanguage(val title: String, val code: String) {
  RU("Русский", "RU"),
  EN("English", "EN")
}

enum class VpnProtocol(val displayName: String, val cipher: String, val port: Int) {
  WIREGUARD("WireGuard® High-Throughput", "ChaCha20-Poly1305", 51820),
  SHADOWSOCKS_2022("Shadowsocks-2022 Blind", "AEAD-2022-Blake3", 8388),
  V2RAY_VMESS("V2Ray / VMess Over TLS", "AES-128-GCM", 443),
  IKEV2_IPSEC("IKEv2 / IPsec StrongSwan", "AES-256-GCM", 500)
}

enum class DnsProvider(val displayName: String, val address: String) {
  STARDOM_ZERO_KNOWLEDGE("Stardom Zero-Knowledge (RAM-Only)", "10.64.0.1"),
  CLOUDFLARE_DOH("Cloudflare 1.1.1.1 (DoH)", "1.1.1.1"),
  QUAD9_SECURE("Quad9 Secure Filtered", "9.9.9.9"),
  CUSTOM_ENCRYPTED("Custom Encrypted DNS", "Custom")
}

data class StarServerNode(
  val id: String,
  val starName: String,
  val constellation: String,
  val city: String,
  val countryCode: String,
  val coordinates: String,
  val basePingMs: Int,
  val loadPercent: Int,
  val ipAddress: String,
  val cipher: String = "ChaCha20-Poly1305",
  val isStarred: Boolean = false
)

data class AccountProfile(
  val accountId: String = "STAR-4096-ALPHA",
  val tier: String = "ORBITAL APEX // PRO",
  val publicKey: String = "ed25519:7a4f89d31ce02b66",
  val validUntil: String = "2028.12.31",
  val activeDevices: Int = 3,
  val maxDevices: Int = 5,
  val bandwidthUsedGb: Double = 184.6,
  val totalQuota: String = "UNLIMITED"
)

data class TelemetryState(
  val currentPingMs: Int = 24,
  val downloadSpeedMbps: Float = 0.0f,
  val uploadSpeedMbps: Float = 0.0f,
  val totalDownloadedMb: Double = 0.0,
  val totalUploadedMb: Double = 0.0,
  val sessionDurationSeconds: Long = 0L,
  val assignedVirtualIp: String = "185.220.101.42"
)
