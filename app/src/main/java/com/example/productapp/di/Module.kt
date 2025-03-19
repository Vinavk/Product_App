package com.example.productapp.di

import com.example.productapp.model.Repository
import com.example.productapp.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


const val BASE_URL = "http://sjdev.salesjump.in/server/"

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun ProvideRetrofit(): ApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun ProvideRepo(retrofit: ApiService): Repository {
        return Repository(retrofit)
    }
}