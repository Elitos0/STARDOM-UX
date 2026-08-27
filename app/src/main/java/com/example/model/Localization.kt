package com.example.model

object Localization {

  fun isSecured(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "СОЕДИНЕНИЕ // АКТИВНО"
    AppLanguage.EN -> "LINK // SECURED"
  }

  fun isOffline(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "УЗЕЛ // ОФФЛАЙН"
    AppLanguage.EN -> "NODE // OFFLINE"
  }

  fun statusHeader(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ТЕКУЩИЙ СТАТУС"
    AppLanguage.EN -> "CURRENT STATUS"
  }

  fun powerButtonAction(state: VpnState, lang: AppLanguage): String = when (state) {
    VpnState.DISCONNECTED -> if (lang == AppLanguage.RU) "ПОДКЛЮЧИТЬ" else "INITIALIZE"
    VpnState.RESOLVING_STAR_ROUTE,
    VpnState.HANDSHAKING_CIPHER,
    VpnState.AUTHENTICATING_NODE -> if (lang == AppLanguage.RU) "СОЕДИНЕНИЕ..." else "CONNECTING..."
    VpnState.SECURED -> if (lang == AppLanguage.RU) "ОТКЛЮЧИТЬ" else "DISENGAGE"
    VpnState.DISCONNECTING -> if (lang == AppLanguage.RU) "ОТКЛЮЧЕНИЕ..." else "CLOSING..."
  }

  fun powerButtonStatus(state: VpnState, lang: AppLanguage): String = when (state) {
    VpnState.DISCONNECTED -> if (lang == AppLanguage.RU) "ОТКЛЮЧЕНО" else "DE-ORBITED"
    VpnState.RESOLVING_STAR_ROUTE -> if (lang == AppLanguage.RU) "ПОИСК МАРШРУТА" else "RESOLVING ROUTE"
    VpnState.HANDSHAKING_CIPHER -> if (lang == AppLanguage.RU) "РУКОПОЖАТИЕ ШИФРА" else "CIPHER HANDSHAKE"
    VpnState.AUTHENTICATING_NODE -> if (lang == AppLanguage.RU) "АВТОРИЗАЦИЯ УЗЛА" else "NODE AUTHENTICATION"
    VpnState.SECURED -> if (lang == AppLanguage.RU) "ЗАЩИЩЕНО" else "LINK SECURED"
    VpnState.DISCONNECTING -> if (lang == AppLanguage.RU) "РАЗРЫВ СВЯЗИ" else "DE-ORBITING"
  }

  fun modeRoutingTag(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "МАРШРУТИЗАЦИЯ"
    AppLanguage.EN -> "ROUTING"
  }

  fun modeRoutingTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "АВТО: БЫСТРЫЙ"
    AppLanguage.EN -> "AUTO: STAR ROUTE"
  }

  fun modeRoutingSubtitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "Мин. задержка"
    AppLanguage.EN -> "Optimal Latency"
  }

  fun modeNodeTag(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "УЗЕЛ"
    AppLanguage.EN -> "NODE"
  }

  fun modeNodeTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ВЫБОР УЗЛА"
    AppLanguage.EN -> "MANUAL: STELLAR"
  }

  fun modeNodeSubtitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "Конкретный сервер"
    AppLanguage.EN -> "Specific Star Node"
  }

  fun activeNodeTag(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "АКТИВНЫЙ УЗЕЛ"
    AppLanguage.EN -> "ACTIVE NODE"
  }

  fun autoRouteTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "АВТО: НАИМЕНЬШИЙ ПИНГ"
    AppLanguage.EN -> "AUTO: LOWEST LATENCY"
  }

  fun autoRouteBadge(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "АВТО МАРШРУТ"
    AppLanguage.EN -> "AUTO ROUTE"
  }

  fun loadLabel(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "НАГРУЗКА"
    AppLanguage.EN -> "LOAD"
  }

  // Settings
  fun settingsTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "НАСТРОЙКИ СЕТИ И БЕЗОПАСНОСТИ"
    AppLanguage.EN -> "STELLAR ROUTING & SECURITY CONFIG"
  }

  fun settingsSubtitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ШИФРОВАНИЕ И ЗАЩИТА КАНАЛА"
    AppLanguage.EN -> "KERNEL CIPHER & HARDWARE DEFENSE"
  }

  fun doneBtn(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ГОТОВО ✕"
    AppLanguage.EN -> "DONE ✕"
  }

  fun languageSection(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ЯЗЫК ИНТЕРФЕЙСА"
    AppLanguage.EN -> "INTERFACE LANGUAGE"
  }

  fun protocolSection(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ПРОТОКОЛ ШИФРОВАНИЯ"
    AppLanguage.EN -> "ENCRYPTION PROTOCOL ENGINE"
  }

  fun dnsSection(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "DNS-РЕЗОЛВЕР И ПРИВАТНОСТЬ"
    AppLanguage.EN -> "RESOLVER & DNS ZERO-KNOWLEDGE"
  }

  fun securitySection(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ПАРАМЕТРЫ ЗАЩИТЫ"
    AppLanguage.EN -> "HARDWARE LOCKS & MASKING"
  }

  fun activeStatus(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "АКТИВЕН"
    AppLanguage.EN -> "ACTIVE"
  }

  fun lockedStatus(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ВЫБРАН"
    AppLanguage.EN -> "LOCKED"
  }

  fun killSwitchTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "KILL SWITCH // БЛОКИРОВКА УТЕЧЕК"
    AppLanguage.EN -> "KILL SWITCH // ABSOLUTE LOCK"
  }

  fun killSwitchDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "Блокировать незашифрованный трафик при обрыве соединения"
    AppLanguage.EN -> "Block non-encrypted socket traffic if celestial connection drops"
  }

  fun dnsGuardTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ЗАЩИТА ОТ УТЕЧЕК DNS"
    AppLanguage.EN -> "DNS LEAK DEFENSE"
  }

  fun dnsGuardDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "Маршрутизация всех DNS-запросов через защищенные RAM-туннели"
    AppLanguage.EN -> "Route all host lookup requests through encrypted RAM tunnels"
  }

  fun obfuscationTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "МАСКИРОВКА ТРАФИКА (OBFUSCATION)"
    AppLanguage.EN -> "STEALTH SCRAMBLER (OBFUSCATION)"
  }

  fun obfuscationDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "Маскировать VPN-трафик под обычный HTTPS TLS 1.3"
    AppLanguage.EN -> "Disguise VPN header signatures as ordinary HTTPS TLS 1.3 traffic"
  }

  fun autoWifiTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "АВТОПОДКЛЮЧЕНИЕ К ПУБЛИЧНЫМ WI-FI"
    AppLanguage.EN -> "AUTO-CONNECT ON UNTRUSTED WI-FI"
  }

  fun autoWifiDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "Автоматически подключать быстрый узел при входе в неизвестные сети"
    AppLanguage.EN -> "Engage optimal constellation node on unknown public SSID links"
  }

  // Account
  fun accountDialogTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ИДЕНТИФИКАТОР STARDOM"
    AppLanguage.EN -> "STARDOM CRYPTOGRAPHIC ID"
  }

  fun accountIdentity(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ИДЕНТИФИКАТОР УЗЛА"
    AppLanguage.EN -> "NODE IDENTITY"
  }

  fun accountTier(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ТАРИФНЫЙ ПЛАН"
    AppLanguage.EN -> "CONSTELLATION TIER"
  }

  fun accountPublicKey(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ХЭШ ПУБЛИЧНОГО КЛЮЧА"
    AppLanguage.EN -> "PUBLIC KEY HASH"
  }

  fun accountActiveDevices(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "АКТИВНЫЕ УСТРОЙСТВА"
    AppLanguage.EN -> "ACTIVE SATELLITE LINKS"
  }

  fun accountDevicesSuffix(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "УСТРОЙСТВ"
    AppLanguage.EN -> "LINKS"
  }

  fun accountDataTransferred(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ПЕРЕДАНО ДАННЫХ"
    AppLanguage.EN -> "DATA TRANSFERRED"
  }

  fun accountUnlimited(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "БЕЗЛИМИТНО"
    AppLanguage.EN -> "UNLIMITED"
  }

  fun accountValidUntil(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ДЕЙСТВУЕТ ДО"
    AppLanguage.EN -> "SUBSCRIPTION HORIZON"
  }

  fun copyKeyBtn(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "СКОПИРОВАТЬ КЛЮЧ"
    AppLanguage.EN -> "COPY PUBLIC KEY"
  }

  fun confirmBtn(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ЗАКРЫТЬ ❯"
    AppLanguage.EN -> "CONFIRM ❯"
  }

  fun keyCopiedToast(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "Публичный ключ скопирован"
    AppLanguage.EN -> "Public key copied to clipboard"
  }

  // Server Directory
  fun serverDirectoryTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "СПИСОК СЕРВЕРОВ"
    AppLanguage.EN -> "CONSTELLATION NODE NETWORK"
  }

  fun serverDirectorySubtitle(count: Int, lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "$count АКТИВНЫХ УЗЛОВ ОНЛАЙН"
    AppLanguage.EN -> "$count ACTIVE RELAY NODES ONLINE"
  }

  fun searchPrefix(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ПОИСК >"
    AppLanguage.EN -> "SEARCH >"
  }

  fun searchPlaceholder(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ПОИСК ПО УЗЛУ ИЛИ ГОРОДУ..."
    AppLanguage.EN -> "FILTER BY STAR OR CITY..."
  }

  fun closeBtn(lang: AppLanguage): String = when (lang) {
    AppLanguage.RU -> "ЗАКРЫТЬ ✕"
    AppLanguage.EN -> "CLOSE ✕"
  }
}
