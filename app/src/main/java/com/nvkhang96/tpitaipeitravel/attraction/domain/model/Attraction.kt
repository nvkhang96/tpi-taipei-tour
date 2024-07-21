package com.nvkhang96.tpitaipeitravel.attraction.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Attraction(
    val id: Int = -1,
    val name: String = "",
    val introduction: String = "",
    val address: String = "",
    val modified: String = "",
    val url: String = "",
    val images: List<AttractionImage> = emptyList(),
)
