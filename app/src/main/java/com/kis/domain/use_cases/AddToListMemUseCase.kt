package com.kis.domain.use_cases

import com.kis.classes.Meme
import com.kis.data.MemeRepositoryImpl

class AddToListMemUseCase(private val repository: MemeRepositoryImpl) {

    suspend fun addToListMemeUseCase(item: Meme) {
        repository.addToListMemeUseCase(item)
    }
}