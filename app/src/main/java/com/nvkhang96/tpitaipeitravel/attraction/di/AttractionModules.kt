package com.nvkhang96.tpitaipeitravel.attraction.di

import org.koin.dsl.module

val attractionModules = module {
    includes(
        attractionNetworkModule,
        attractionRepositoryModule,
        attractionViewModelModule,
    )
}