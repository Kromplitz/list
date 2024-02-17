package com.example.list

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class ApiClient @Inject constructor() {
    val client = Retrofit.Builder()
      .client(OkHttpClient())
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl("https://akabab.github.io")
      //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build() }