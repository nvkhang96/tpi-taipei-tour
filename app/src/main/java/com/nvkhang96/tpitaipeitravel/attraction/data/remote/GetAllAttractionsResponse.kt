package com.nvkhang96.tpitaipeitravel.attraction.data.remote


import com.nvkhang96.tpitaipeitravel.attraction.data.remote.dto.AttractionDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllAttractionsResponse(
    @SerialName("data")
    val attractions: List<AttractionDto?>? = listOf(),
    @SerialName("total")
    val total: Int? = 0,
)