package com.example.list

import android.provider.MediaStore.Images
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

//interface ApiInterface{
  //  @GET("/superhero-api/api/all.json")
   // fun getHeroes(): Single<List<Hero>>}

interface ApiInterface {
    @GET("/superhero-api/api/all.json")
    suspend fun getHeroes(@Path("all") name: String, images: String):List<Hero>?
}