package com.kis.domain.use_cases

import com.kis.data.MemeRepositoryImpl

class DeleteAllToListUseCase(private val repository: MemeRepositoryImpl) {

    suspend fun deleteAllToListUseCase() {
        repository.deleteAllToListUseCase()
    }
}