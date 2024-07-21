package com.nvkhang96.tpitaipeitravel.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nvkhang96.tpitaipeitravel.core.domain.repository.KeyValueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreRepository(
    private val dataStore: DataStore<Preferences>,
) : KeyValueRepository {
    override suspend fun putString(key: String, value: String) {
        dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    override suspend fun getString(key: String): Flow<String?> {
        return dataStore.data.map { it[stringPreferencesKey(key)] }
    }
}