package com.gllce.artbook.api


import com.gllce.artbook.model.ImageResponse
import com.gllce.artbook.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchKey: String,
        @Query("key") apiKey: String = API_KEY,
    ): Response<ImageResponse>
}