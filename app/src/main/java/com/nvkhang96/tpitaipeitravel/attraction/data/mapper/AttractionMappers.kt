package com.nvkhang96.tpitaipeitravel.attraction.data.mapper

import com.nvkhang96.tpitaipeitravel.attraction.data.remote.dto.AttractionDto
import com.nvkhang96.tpitaipeitravel.attraction.data.remote.dto.ImageDto
import com.nvkhang96.tpitaipeitravel.attraction.domain.model.Attraction
import com.nvkhang96.tpitaipeitravel.attraction.domain.model.AttractionImage

fun AttractionDto.toAttraction() = Attraction(
    id = id ?: -1,
    name = name.orEmpty(),
    introduction = introduction.orEmpty(),
    address = address.orEmpty(),
    modified = modified.orEmpty(),
    url = url.orEmpty(),
    images = images?.filterNotNull()?.map { it.toAttractionImage() } ?: emptyList(),
)

fun ImageDto.toAttractionImage() = AttractionImage(
    src = src.orEmpty(),
)