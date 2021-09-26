package com.gllce.artbook.api

import androidx.lifecycle.LiveData
import com.gllce.artbook.model.ImageResponse
import com.gllce.artbook.roomdb.Art
import com.gllce.artbook.util.Resource

interface ArtRepositoryInterface {
    suspend fun insertArt(art: Art)
    suspend fun deleteArt(art: Art)
    fun getArt(): LiveData<List<Art>>
    suspend fun searchImage(imageString: String) : Resource<ImageResponse>
}