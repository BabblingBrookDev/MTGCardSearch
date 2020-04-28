package com.babblingbrook.mtgcardsearch.data.remote

import com.babblingbrook.mtgcardsearch.model.CardData
import com.babblingbrook.mtgcardsearch.model.CardIdentifier
import com.babblingbrook.mtgcardsearch.model.Feed
import com.babblingbrook.mtgcardsearch.model.SearchData
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/cards/collection")
    @JsonResponse
    fun getCards(@Body cardIdentifier: CardIdentifier): Deferred<ApiResponse<CardData>>

    @GET("/cards/autocomplete")
    @JsonResponse
    fun search(
        @Query("q") query: String?
    ): Deferred<ApiResponse<SearchData>>

    @GET
    @XmlResponse
    fun getFeedsAsync(@Url url: String): Deferred<ApiResponse<Feed>>
}
