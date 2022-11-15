package com.kis.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kis.retrofit.MemsApiRetro
import com.kis.retrofit.RetroRequestT

class MainViewModel: ViewModel() {

    private final val TAG = "MainViewModelTAG"

    suspend fun getAllMemes(): String {
        val quetesApi = RetroRequestT.getInstance().create(MemsApiRetro::class.java)
        var answer: String = ""
        val resultApiRequest = quetesApi.getQuotesList()
        if (resultApiRequest != null) {
            Log.d(TAG, resultApiRequest.body().toString())
            answer = resultApiRequest.body().toString()
            return answer
        }
        return answer
    }

    suspend fun getOneMemes(): String {
        val requestApi = RetroRequestT.getInstance().create(MemsApiRetro::class.java)
        var answer: String? = null
        val resultApiResuest = requestApi.getQuotesList()
        if (resultApiResuest != null) {
            answer = resultApiResuest.body()?.`data`?.memes?.get(0)?.url
            return answer ?: "null"
        }
        return answer ?: "null"
    }
}