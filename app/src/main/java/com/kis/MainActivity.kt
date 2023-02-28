package com.kis

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kis.adapters.RecycleViewMemAdapter
import com.kis.classes.Meme
import com.kis.databinding.ActivityMainBinding
import com.kis.viewModels.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import okhttp3.*

class MainActivity : AppCompatActivity() {


    private final val TAG = "MainActivityModelTAG"
    // add coroutine
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    // recycle view
    private lateinit var recycleMemeAdapter: RecycleViewMemAdapter
    // create binding inflate
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val listRandN = mutableListOf<Int>()
    private val viewModel: MainViewModel by viewModels()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // request view mode
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)



        // get one item mem with async thread background
        var answser: Meme = Meme()
        val deffRetro: Deferred<Meme> = scope.async {
            answser = viewModel.getOneMemes(0)
            answser
        }
        hideHeadBar()
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

    }

//    private fun onSwipeListener(recycleList: RecyclerListener) {
//        val swipeCallback = object : ItemTouchHelper.SimpleCallback(
//            0,
//            ItemTouchHelper.UP
//        ) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                val curentElement = recycleMemeAdapter.currentList[viewHolder.adapterPosition]
//                scope.launch {
//                    viewModel.deleteFromList(curentElement)
//                }
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                TODO("Not yet implemented")
//            }
//        }
//
//    }

    private fun initRecycleView() {
        val recycleViewMeme = binding.memRv
        with(recycleViewMeme) {
            recycleMemeAdapter = RecycleViewMemAdapter()
            adapter = recycleMemeAdapter
        }

        itemListener()
    }

    private fun itemListener() {
        recycleMemeAdapter.onItemClickListener = {
            Picasso.with(this)
                .load(it.url)
                .into(binding.imageView)
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


    private fun hideHeadBar() {
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }


}