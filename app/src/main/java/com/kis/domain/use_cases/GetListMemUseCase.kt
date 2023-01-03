package com.kis.domain.use_cases

import androidx.lifecycle.LiveData
import com.kis.classes.Meme
import com.kis.data.MemeRepositoryImpl

class GetListMemUseCase(private val repository: MemeRepositoryImpl) {

    fun getListMemeUseCase(): LiveData<List<Meme>> {
        return repository.getListMemeUseCase()
    }

}