package com.example.list

import io.reactivex.Single
import retrofit2.Retrofit

class Repository(private val client:Retrofit) {
    suspend fun getHeroByName(name: String, images: String): Single<List<Hero>> {
        val apiInterface = client.create(ApiInterface::class.java)
        return apiInterface.getHeroes(name, images)
    }

}