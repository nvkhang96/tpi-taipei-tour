package com.nvkhang96.tpitaipeitravel.attraction.domain.model

import com.nvkhang96.tpitaipeitravel.core.domain.enums.Language

data class AttractionQuery(
    val language: String = Language.en.value,
)
