package com.nvkhang96.tpitaipeitravel.attraction.domain.repository

import androidx.paging.PagingData
import com.nvkhang96.tpitaipeitravel.attraction.domain.model.Attraction
import com.nvkhang96.tpitaipeitravel.attraction.domain.model.AttractionQuery
import kotlinx.coroutines.flow.Flow

interface AttractionRepository {
    fun getAllAttractions(query: AttractionQuery): Flow<PagingData<Attraction>>
}