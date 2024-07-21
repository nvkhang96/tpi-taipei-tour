package com.nvkhang96.tpitaipeitravel.attraction.di

import com.nvkhang96.tpitaipeitravel.attraction.data.remote.AttractionService
import org.koin.dsl.module
import retrofit2.Retrofit

val attractionNetworkModule = module {
    single<AttractionService> {
        get<Retrofit>().create(AttractionService::class.java)
    }
}