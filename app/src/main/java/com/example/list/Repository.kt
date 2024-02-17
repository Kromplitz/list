package com.example.list

import io.reactivex.Single
import retrofit2.Retrofit

class Repository(val apiClient: ApiClient) {
    suspend fun getHeroByName(): List<Hero>? {
        val apiInterface = apiClient.client.create(ApiInterface::class.java)
        return apiInterface.getHeroes()
    }

}