package com.kis.viewModels

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kis.classes.Meme
import com.kis.classes.state_clases.LoadState
import com.kis.classes.state_clases.StateAnimation
import com.kis.retrofit.MemsApiRetro
import com.kis.retrofit.RetroRequestT
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private final val TAG = "MainViewModelTAG"
        // create list what we observe
    private var _listMemsUsr = MutableLiveData<List<Meme>>()
    val listMemsUsr: LiveData<List<Meme>>
        get() = _listMemsUsr

    private var _memRotation = MutableLiveData<StateAnimation>()
    val memRotation: LiveData<StateAnimation>
        get() = _memRotation


    private fun loadImageAnimate(view: ImageView) {
        val animator = ObjectAnimator.ofFloat(view, View.ROTATION, 360f, 0f)
        animator.duration = 1000
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                animator.start()
            }
        })
        animator.start()

    }

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

    fun getFiveMemes(randNumb: List<Int>) {
        viewModelScope.launch {
            _listMemsUsr.value
            var fiveMemeList = mutableListOf<Meme>()
            for (i in randNumb) {
                fiveMemeList.add(getOneMemes(i))
                Log.d(TAG, "Random number: $i")
            }
            Log.d(TAG, fiveMemeList.toString())
            _listMemsUsr.value = fiveMemeList
//            return fiveMemeList
        }
    }

    suspend fun getOneMemes(numbM: Int): Meme {
        val requestApi = RetroRequestT.getInstance().create(MemsApiRetro::class.java)
        val meme = Meme()
        val resultApiResuest = requestApi.getQuotesList()
        if (resultApiResuest != null) {
            val memesData = resultApiResuest.body()?.`data`?.memes
            meme.id = memesData?.get(numbM)?.id.toString()
            meme.url = memesData?.get(numbM)?.url.toString()
            meme.name = memesData?.get(numbM)?.name.toString()
            meme.box_count = memesData?.get(numbM)?.box_count!!
            meme.height = memesData[numbM].height
            meme.width = memesData[numbM].width
            val memsSize = memesData.size
            Log.d(TAG, "${memsSize}")
            return meme
        }
        return meme
    }


}