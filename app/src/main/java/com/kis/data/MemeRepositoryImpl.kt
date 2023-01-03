package com.kis.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kis.classes.Meme
import com.kis.domain.use_cases.MemRepository
import com.kis.retrofit.MemsApiRetro
import com.kis.retrofit.RetroRequestT

class MemeRepositoryImpl(private val memUserDb: UserRoomDao): MemRepository {

    val TAG = "MemeRepositoryImpl"

    override suspend fun addToListMemeUseCase(item: Meme) {
        memUserDb.insertDbUser(item)
    }

    override suspend fun deleteItemInListUseCase(item: Meme) {
        memUserDb.deleteSelectItem(item.id)
    }

    override suspend fun deleteAllToListUseCase() {
        memUserDb.deleteAllFromUser()
    }

    override fun getListMemeUseCase():LiveData<List<Meme>> {
        return memUserDb.getAllHaveUser()
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