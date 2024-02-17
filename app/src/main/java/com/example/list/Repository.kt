package com.example.list

import io.reactivex.Single
import retrofit2.Retrofit

class Repository(val apiClient: ApiClient) {
    suspend fun getHeroByName(name: String, images: String): List<Hero>? {
        val apiInterface = apiClient.client.create(ApiInterface::class.java)
        return apiInterface.getHeroes(name, images)
    }

}