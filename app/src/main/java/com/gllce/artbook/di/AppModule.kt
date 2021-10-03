package com.gllce.artbook.di

import android.content.Context
import androidx.room.Room
import com.gllce.artbook.api.ArtRepository
import com.gllce.artbook.api.ArtRepositoryInterface
import com.gllce.artbook.api.ImageApi
import com.gllce.artbook.roomdb.ArtDao
import com.gllce.artbook.roomdb.ArtDatabase
import com.gllce.artbook.util.Util.BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, ArtDatabase::class.java, "ArtBookDB"
    ).build()


    @Singleton
    @Provides
    fun provideDao(database: ArtDatabase) = database.artDao()


    @Singleton
    @Provides
    fun provideImageApi() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(ImageApi::class.java)

    @Singleton
    @Provides
    fun provideArtRepository(artDao: ArtDao, imageApi: ImageApi) =
        ArtRepository(artDao, imageApi) as ArtRepositoryInterface
}