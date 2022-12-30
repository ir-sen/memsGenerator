package com.kis.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kis.classes.Meme
import com.kis.domain.use_cases.MemRepository

class MemeRepositoryImpl(private val memUserDb: UserRoomDao): MemRepository {

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
}