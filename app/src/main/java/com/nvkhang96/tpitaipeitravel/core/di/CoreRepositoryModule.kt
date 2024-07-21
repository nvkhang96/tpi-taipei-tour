package com.nvkhang96.tpitaipeitravel.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.paging.PagingConfig
import com.nvkhang96.tpitaipeitravel.core.data.TRAVEL_TAIPEI_PAGE_SIZE
import com.nvkhang96.tpitaipeitravel.core.data.repository.DataStoreRepository
import com.nvkhang96.tpitaipeitravel.core.data.repository.dataStore
import com.nvkhang96.tpitaipeitravel.core.domain.repository.KeyValueRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreRepositoryModule = module {
    single<PagingConfig> {
        PagingConfig(
            pageSize = TRAVEL_TAIPEI_PAGE_SIZE,
            enablePlaceholders = false,
        )
    }

    single<DataStore<Preferences>> { androidContext().dataStore }
    single<KeyValueRepository> { DataStoreRepository(get()) }
}