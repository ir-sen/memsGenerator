package com.kis.domain.use_cases

import com.kis.classes.Meme
import com.kis.data.MemeRepositoryImpl

class GetOneMemUseCase(private val repositoryImpl: MemeRepositoryImpl) {
    // return one mem from service
    suspend fun getOneMemuseCase(numbM: Int): Meme {
        return repositoryImpl.getOneMemes(numbM)
    }
}