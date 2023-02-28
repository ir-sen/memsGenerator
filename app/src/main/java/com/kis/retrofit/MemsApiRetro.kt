package com.kis.retrofit

import com.kis.classes.ItemMems2
import retrofit2.http.GET

interface MemsApiRetro {

    @GET("/get_memes")
    suspend fun getQuotesList(): retrofit2.Response<ItemMems2>
}