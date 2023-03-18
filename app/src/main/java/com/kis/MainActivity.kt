package com.kis

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
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

    // recycle view
    private lateinit var recycleMemeAdapter: RecycleViewMemAdapter
    // create binding inflate
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val listRandN = mutableListOf<Int>()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // request view mode
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
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
            animateGone()
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

        val logAnswer = runBlocking {
            deffRetro.await()
        }
        Log.d(TAG, logAnswer.url)


        Picasso.with(this)
            .load(logAnswer.url)
            .into(binding.choiceShowIv)
    }

    private fun animateGone() {
        val animator = ObjectAnimator.ofFloat(binding.getRandomBtn, View.TRANSLATION_X, 360f)
        animator.duration = 1000
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                binding.getRandomBtn.visibility = View.GONE
            }
        })
        animator.start()

    }
    // swipe listener
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
        binding.memRv.visibility = View.VISIBLE
        val recycleViewMeme = binding.memRv
        with(recycleViewMeme) {
            recycleMemeAdapter = RecycleViewMemAdapter()
            adapter = recycleMemeAdapter
        }

        itemListener()
    }

    private fun itemListener() {
        binding.choiceShowIv.visibility = View.VISIBLE
        recycleMemeAdapter.onItemClickListener = {
            Picasso.with(this)
                .load(it.url)
                .into(binding.choiceShowIv)
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
            .into(binding.choiceShowIv)
    }


    private fun hideHeadBar() {
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }


}