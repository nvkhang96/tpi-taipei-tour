package com.nvkhang96.tpitaipeitravel.attraction.data.remote

import com.nvkhang96.tpitaipeitravel.core.data.TRAVEL_TAIPEI_DEFAULT_PAGE_INDEX
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AttractionService {
    @GET("{lang}/Attractions/All")
    suspend fun getAttractions(
        @Path("lang") language: String,
        @Query("page") page: Int = TRAVEL_TAIPEI_DEFAULT_PAGE_INDEX,
    ): GetAllAttractionsResponse
}