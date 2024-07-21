package com.nvkhang96.tpitaipeitravel.attraction.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("name")
    val name: String? = ""
)