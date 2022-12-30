package com.kis.domain.use_cases

import androidx.lifecycle.LiveData
import com.kis.classes.Meme

class GetListMemUseCase(private val repository: MemRepository) {

    fun getListMemeUseCase(): LiveData<List<Meme>> {
        return repository.getListMemeUseCase()
    }

}