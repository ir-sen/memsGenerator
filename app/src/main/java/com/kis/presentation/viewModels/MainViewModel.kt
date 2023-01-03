package com.kis.presentation.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kis.classes.Meme
import com.kis.domain.use_cases.*
import com.kis.retrofit.MemsApiRetro
import com.kis.retrofit.RetroRequestT
import kotlinx.coroutines.launch


class MainViewModel(
    val addToListMemUseCase: AddToListMemUseCase,
    val delteImteListUseCase: DeleteItemInListUseCase,
    val deleteAllToListUseCase: DeleteAllToListUseCase,
    val getListMemeUseCase: GetListMemUseCase,
    val getOneMemUseCse: GetOneMemUseCase
    ): ViewModel() {

    private final val TAG = "MainViewModelTAG"
        // create list what we observe
    private var _listMemsUsr = MutableLiveData<List<Meme>>()
    val listMemsUsr: LiveData<List<Meme>>
        get() = _listMemsUsr


    suspend fun getAllMemes(): String {
        val quetesApi = RetroRequestT.getInstance().create(MemsApiRetro::class.java)
        var answer: String = ""
        val resultApiRequest = quetesApi.getQuotesList()
        if (resultApiRequest != null) {
            Log.d(TAG, resultApiRequest.body().toString())
            answer = resultApiRequest.body().toString()
            return answer
        }
        return answer
    }

    fun deleteFromList(meme: Meme) {
        viewModelScope.launch {
            _listMemsUsr.value
        }
    }

    fun getOneMem(numbM: Int) {
        viewModelScope.launch {
            _listMemsUsr.value = listOf(getOneMemUseCse.getOneMemuseCase(numbM))
        }

    }

    fun getFiveMemes(randNumb: List<Int>) {

    }

    
}