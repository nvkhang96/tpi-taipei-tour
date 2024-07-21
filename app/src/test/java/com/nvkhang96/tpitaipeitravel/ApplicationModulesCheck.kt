package com.nvkhang96.tpitaipeitravel

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class ApplicationModulesCheck: KoinTest {
    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkApplicationModules() {
        applicationModules.verify(
            extraTypes = listOf(
                Boolean::class
            )
        )
    }
}