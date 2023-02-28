package com.kis.domain.use_cases

import com.kis.classes.Meme

class DeleteItemInListUseCase(private val repository: MemRepository) {

    suspend fun deleteItemInListUseCase(item: Meme) {
        repository.deleteItemInListUseCase(item)
    }
}