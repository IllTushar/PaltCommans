package com.example.test.Api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named("Reqres")
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Named("Movie")
    fun provideMovie(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://TMDB-Movies-and-TV-Shows-API-by-APIRobots.proxy-production.allthingsdev.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Named("ReqresService")
    fun provideApiService(@Named("Reqres")retrofit: Retrofit): Services =
        retrofit.create(Services::class.java)

    @Provides
    @Named("MovieService")
    fun provideApiServiceMovie(@Named("Movie") retrofit: Retrofit): Services =
        retrofit.create(Services::class.java)
}