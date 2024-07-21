package com.nvkhang96.tpitaipeitravel.core.di

import org.koin.dsl.module

val coreModules = module {
    includes(
        coreNetworkModule,
        coreRepositoryModule,
    )
}