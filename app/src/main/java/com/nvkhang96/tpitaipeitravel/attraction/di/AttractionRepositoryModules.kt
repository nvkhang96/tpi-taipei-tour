package com.nvkhang96.tpitaipeitravel.attraction.di

import com.nvkhang96.tpitaipeitravel.attraction.data.repository.AttractionRepositoryImpl
import com.nvkhang96.tpitaipeitravel.attraction.domain.repository.AttractionRepository
import org.koin.dsl.module

val attractionRepositoryModule = module {
    single<AttractionRepository> { AttractionRepositoryImpl(get(), get()) }
}