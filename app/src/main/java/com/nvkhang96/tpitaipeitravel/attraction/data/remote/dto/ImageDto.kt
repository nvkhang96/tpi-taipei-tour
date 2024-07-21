package com.nvkhang96.tpitaipeitravel.attraction.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    @SerialName("ext")
    val ext: String? = "",
    @SerialName("src")
    val src: String? = "",
    @SerialName("subject")
    val subject: String? = ""
)