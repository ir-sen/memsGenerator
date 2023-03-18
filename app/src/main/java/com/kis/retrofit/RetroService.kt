package com.kis.retrofit

import com.kis.classes.ItemMems2
import retrofit2.http.GET

interface RetroService {

    @GET()
    fun getListmems(): ItemMems2


    @GET()
    fun getTest(): String

}