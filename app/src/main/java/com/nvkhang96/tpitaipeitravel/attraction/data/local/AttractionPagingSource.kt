package com.nvkhang96.tpitaipeitravel.attraction.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nvkhang96.tpitaipeitravel.attraction.data.mapper.toAttraction
import com.nvkhang96.tpitaipeitravel.attraction.data.remote.AttractionService
import com.nvkhang96.tpitaipeitravel.attraction.domain.model.Attraction
import com.nvkhang96.tpitaipeitravel.attraction.domain.model.AttractionQuery
import com.nvkhang96.tpitaipeitravel.core.data.TRAVEL_TAIPEI_DEFAULT_PAGE_INDEX
import com.nvkhang96.tpitaipeitravel.core.data.TRAVEL_TAIPEI_PAGE_SIZE
import kotlin.math.ceil

class AttractionPagingSource(
    private val service: AttractionService,
    private val query: AttractionQuery,
) : PagingSource<Int, Attraction>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Attraction> {
        try {
            val nextPageNumber = params.key ?: TRAVEL_TAIPEI_DEFAULT_PAGE_INDEX
            val response = service.getAttractions(
                language = query.language,
                page = nextPageNumber,
            )

            val totalPages = (response.total ?: TRAVEL_TAIPEI_DEFAULT_PAGE_INDEX).toDouble()
            val maxPageIndex = ceil(totalPages / TRAVEL_TAIPEI_PAGE_SIZE)

            return LoadResult.Page(
                data = response.attractions
                    ?.filterNotNull()
                    ?.map { it.toAttraction() } ?: emptyList(),
                prevKey = null,
                nextKey = if (nextPageNumber < maxPageIndex) nextPageNumber + 1 else null,
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Attraction>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}