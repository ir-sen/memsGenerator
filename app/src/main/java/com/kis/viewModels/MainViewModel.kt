package com.kis.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kis.classes.Meme
import com.kis.retrofit.MemsApiRetro
import com.kis.retrofit.RetroRequestT
import kotlinx.coroutines.launch
import kotlin.random.Random



class MainViewModel: ViewModel() {

    private final val TAG = "MainViewModelTAG"
        // create list what we observe
    private var _listMemsUsr = MutableLiveData<List<Meme>>()
    val listMemsUsr: LiveData<List<Meme>>
        get() = _listMemsUsr


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

    fun getFiveMemes(randNumb: List<Int>) {
        viewModelScope.launch {
            _listMemsUsr.value
            var fiveMemeList = mutableListOf<Meme>()
            for (i in randNumb) {
                fiveMemeList.add(getOneMemes(i))
                Log.d(TAG, "Random number: $i")
            }
            Log.d(TAG, fiveMemeList.toString())
            _listMemsUsr.value = fiveMemeList
//            return fiveMemeList
        }
    }

    suspend fun getOneMemes(numbM: Int): Meme {
        val requestApi = RetroRequestT.getInstance().create(MemsApiRetro::class.java)
        var answer: String? = null
        var meme = Meme()
        val resultApiResuest = requestApi.getQuotesList()
        if (resultApiResuest != null) {
            answer = resultApiResuest.body()?.`data`?.memes?.get(numbM)?.url
            val memesData = resultApiResuest.body()?.`data`?.memes
            meme.id = memesData?.get(numbM)?.id.toString()
            meme.url = memesData?.get(numbM)?.url.toString()
            meme.name = memesData?.get(numbM)?.name.toString()
            meme.box_count = memesData?.get(numbM)?.box_count!!
            meme.height = memesData[numbM].height
            meme.width = memesData[numbM].width
            val memsSize = memesData.size
            Log.d(TAG, "${memsSize}")
            return meme
        }
        return meme
    }
}