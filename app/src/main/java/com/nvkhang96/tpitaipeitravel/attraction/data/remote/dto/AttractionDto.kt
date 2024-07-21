package com.nvkhang96.tpitaipeitravel.attraction.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttractionDto(
    @SerialName("address")
    val address: String? = "",
    @SerialName("category")
    val category: List<CategoryDto?>? = listOf(),
    @SerialName("distric")
    val district: String? = "",
    @SerialName("elong")
    val longitude: Double? = 0.0,
    @SerialName("email")
    val email: String? = "",
    @SerialName("facebook")
    val facebook: String? = "",
    @SerialName("fax")
    val fax: String? = "",
    @SerialName("files")
    val files: List<FileDto?>? = listOf(),
    @SerialName("friendly")
    val friendlyList: List<FriendlyDto?>? = listOf(),
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("images")
    val images: List<ImageDto?>? = listOf(),
    @SerialName("introduction")
    val introduction: String? = "",
    @SerialName("links")
    val links: List<LinkDto?>? = listOf(),
    @SerialName("modified")
    val modified: String? = "",
    @SerialName("months")
    val months: String? = "",
    @SerialName("name")
    val name: String? = "",
    @SerialName("name_zh")
    val nameZh: String? = "",
    @SerialName("nlat")
    val latitude: Double? = 0.0,
    @SerialName("official_site")
    val officialSite: String? = "",
    @SerialName("open_status")
    val openStatus: Int? = 0,
    @SerialName("open_time")
    val openTime: String? = "",
    @SerialName("remind")
    val remind: String? = "",
    @SerialName("service")
    val services: List<ServiceDto?>? = listOf(),
    @SerialName("staytime")
    val stayTime: String? = "",
    @SerialName("target")
    val targets: List<TargetDto?>? = listOf(),
    @SerialName("tel")
    val tel: String? = "",
    @SerialName("ticket")
    val ticket: String? = "",
    @SerialName("url")
    val url: String? = "",
    @SerialName("zipcode")
    val zipcode: String? = ""
)