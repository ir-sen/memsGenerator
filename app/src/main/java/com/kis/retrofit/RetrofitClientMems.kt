package com.kis.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientMems {

    private const val baseURLListMems = "https://api.imgflip.com/get_memes"


    fun getClient(): Retrofit {
        return Retrofit.Builder().baseUrl(baseURLListMems)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}