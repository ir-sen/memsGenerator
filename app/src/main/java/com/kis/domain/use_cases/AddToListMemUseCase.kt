package com.kis.domain.use_cases

import com.kis.classes.Meme

class AddToListMemUseCase(private val repository: MemRepository) {

    suspend fun addToListMemeUseCase(item: Meme) {
        repository.addToListMemeUseCase(item)
    }
}