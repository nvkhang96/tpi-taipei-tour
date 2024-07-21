package com.nvkhang96.tpitaipeitravel.attraction.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nvkhang96.tpitaipeitravel.attraction.data.local.AttractionPagingSource
import com.nvkhang96.tpitaipeitravel.attraction.data.remote.AttractionService
import com.nvkhang96.tpitaipeitravel.attraction.domain.model.Attraction
import com.nvkhang96.tpitaipeitravel.attraction.domain.model.AttractionQuery
import com.nvkhang96.tpitaipeitravel.attraction.domain.repository.AttractionRepository
import com.nvkhang96.tpitaipeitravel.core.data.TRAVEL_TAIPEI_PAGE_SIZE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn

class AttractionRepositoryImpl(
    private val service: AttractionService,
    private val pagingConfig: PagingConfig,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : AttractionRepository {
    override fun getAllAttractions(query: AttractionQuery): Flow<PagingData<Attraction>> =
        Pager(
            config = pagingConfig,
            pagingSourceFactory = { AttractionPagingSource(service, query) },
        ).flow.flowOn(dispatcher)
}