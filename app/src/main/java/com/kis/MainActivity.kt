package com.kis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.kis.classes.Meme
import com.kis.databinding.ActivityMainBinding
import com.kis.retrofit.MemsApiRetro
import com.kis.retrofit.RetroRequestT
import com.kis.viewModels.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import okhttp3.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // what we need show and like and save
    // we get all lings with index in free api

    lateinit var viewModel: MainViewModel
    private final val TAG = "MainActivityModelTAG"
    // add coroutine
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private var indexMem = 0

    // create data base where we save card

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var userListShow = listOf<Meme>()
    private val listRandN = mutableListOf<Int>()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // example from geek
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // get one item mem
        var answser: Meme = Meme()
        val deffRetro: Deferred<Meme> = scope.async {
            answser = viewModel.getOneMemes(0)
            answser
        }




        viewModel.listMemsUsr.observe(this) {
            Log.d(TAG, "Observer list: $it")
        }

        binding.getRandomBtn.setOnClickListener{
            listRandN.clear()
            for (i in 0..4) {
                val random = (0..99).random()
                listRandN.add(random)
            }
            GlobalScope.launch {
                val pe = viewModel.getFiveMemes(listRandN)
                Log.d(TAG, "This is pe: $pe")
            }

            Log.d(TAG, listRandN.toString())
        }

        var logAnswer = runBlocking {
            deffRetro.await()
        }
        Log.d(TAG, logAnswer.url)



        Picasso.with(this)
            .load(logAnswer.url)
            .into(binding.imageView)
        initBtnListeners()

    }

    private fun getMems(indexMem: Int) {
        var answser: Meme = Meme()
        val deffRetro: Deferred<Meme> = scope.async {
            answser = viewModel.getOneMemes(indexMem)
            answser
        }

        var logAnswer = runBlocking {
            deffRetro.await()
        }
        Log.d(TAG, logAnswer.url)

        Picasso.with(this)
            .load(logAnswer.url)
            .into(binding.imageView)
    }

    private fun initBtnListeners() {

        binding.nextBtn.setOnClickListener {
            indexMem++
            getMems(indexMem)
        }
    }




}