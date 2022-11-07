package com.kis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kis.retrofit.RetroService
import com.kis.retrofit.RetrofitClientMems

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val testRepository = RetrofitClientMems.getClient().create(RetroService::class.java)


    }


}