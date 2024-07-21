package com.nvkhang96.tpitaipeitravel.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface KeyValueRepository {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): Flow<String?>

    companion object {
        const val KEY_LANGUAGE = "language"
        const val KEY_SELECTED_ATTRACTION = "selectedAttraction"
        const val KEY_SELECTED_URL = "selectedUrl"
    }
}