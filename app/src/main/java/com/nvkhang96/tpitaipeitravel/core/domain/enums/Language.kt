package com.nvkhang96.tpitaipeitravel.core.domain.enums

enum class Language(val value: String) {
    zhTw("zh-TW"),
    zhCn("zh-CN"),
    en("en"),
    ja("ja"),
    ko("ko"),
    es("es"),
    id("id"),
    th("th"),
    vi("vi");

    fun getApiPath(): String =  this.value.lowercase()

    fun getAndroidLocaleCode(): String = when (this) {
        id -> "hi"
        else -> this.value
    }

    companion object {
        fun fromValue(value: String?): Language? = entries.firstOrNull { it.value == value }
    }
}