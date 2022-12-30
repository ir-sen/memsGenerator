package com.kis.domain.use_cases

class DelteAllToListUseCase(private val repository: MemRepository) {

    suspend fun deleteAllToListUseCase() {
        repository.deleteAllToListUseCase()
    }
}