package com.kis

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kis.adapters.RecycleViewMemAdapter
import com.kis.classes.Meme
import com.kis.databinding.ActivityMainBinding
import com.kis.viewModels.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import okhttp3.*

class MainActivity : AppCompatActivity() {


    lateinit var viewModel: MainViewModel
    private final val TAG = "MainActivityModelTAG"
    // add coroutine
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private var indexMem = 0
    // recycle view
    private lateinit var recycleMemeAdapter: RecycleViewMemAdapter

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
        initRecycleView()
        //realization recycle view
        viewModel.listMemsUsr.observe(this) {
            recycleMemeAdapter.submitList(it)
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

    private fun initRecycleView() {
        val recycleViewMeme = binding.memRv
        with(recycleViewMeme) {
            recycleMemeAdapter = RecycleViewMemAdapter()
            adapter = recycleMemeAdapter
            
        }
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