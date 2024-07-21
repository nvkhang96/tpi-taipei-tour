package com.nvkhang96.tpitaipeitravel.attraction.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileDto(
    @SerialName("src")
    val src: String? = "",
    @SerialName("subject")
    val subject: String? = "",
    @SerialName("ext")
    val ext: String? = "",
)
