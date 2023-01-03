package com.kis.domain.use_cases

import com.kis.classes.Meme
import com.kis.data.MemeRepositoryImpl

class DeleteItemInListUseCase(private val repository: MemeRepositoryImpl) {

    suspend fun deleteItemInListUseCase(item: Meme) {
        repository.deleteItemInListUseCase(item)
    }
}