package com.kis.domain.use_cases

import androidx.lifecycle.LiveData
import com.kis.classes.Meme

interface MemRepository {

    suspend fun addToListMemeUseCase(item: Meme)

    suspend fun deleteItemInListUseCase(item: Meme)

    suspend fun deleteAllToListUseCase()

    fun getListMemeUseCase(): LiveData<List<Meme>>

}