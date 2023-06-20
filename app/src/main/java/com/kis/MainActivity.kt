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

    private lateinit var animatorObject: ObjectAnimator

    // recycle view
    private lateinit var recycleMemeAdapter: RecycleViewMemAdapter
    // create binding inflate
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val listRandN = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // request view mode
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        initTools()
        // get one item mem with async thread background
        var answser: Meme = Meme()
        val deffRetro: Deferred<Meme> = scope.async {
            answser = viewModel.getOneMemes(0)
            answser
        }
        hideHeadBar()
        initRecycleView()
        //realization recycle view ( fill recycle view if list change )
        viewModel.listMemsUsr.observe(this) {
            recycleMemeAdapter.submitList(it)
        }



        val logAnswer = runBlocking {
            deffRetro.await()
        }
        Log.d(TAG, logAnswer.url)


//        Picasso.with(this)
//            .load(logAnswer.url)
//            .into(binding.choiceShowIv)
        loadImageAnimate()
    }

    // create animate load image
    private fun loadImageAnimate() {
        animatorObject = ObjectAnimator.ofFloat(binding.choiceShowIv, View.ROTATION, 360f, 0f)
        animatorObject.duration = 1000
        animatorObject.repeatCount = 30
        animatorObject.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
            }

            override fun onAnimationCancel(animation: Animator) {
                super.onAnimationCancel(animation)
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
            }
        })
        animatorObject.start()
    }

    private fun animateGone() {
        val animator = ObjectAnimator.ofFloat(binding.getStartBtn, View.TRANSLATION_Y, 360f)
        animator.duration = 1000
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                binding.getStartBtn.visibility = View.GONE
                binding.getRandomBtn.visibility = View.VISIBLE
            }
        })
        animator.start()

        val animator2 = ObjectAnimator.ofFloat(binding.choiceShowIv, View.ROTATION, 0f, 0f)
        animator2.duration = 500
        animator2.start()

    }


    // init tools
    private fun initTools() {
        // start btn listener
        binding.getStartBtn.setOnClickListener{
            animateGone()
            listRandN.clear()
            fillRecycleView()
            Log.d(TAG, listRandN.toString())
        }


        binding.getRandomBtn.setOnClickListener {
            listRandN.clear()
            fillRecycleView()
        }
    }

    private fun fillRecycleView() {
        animatorObject.cancel()
        for (i in 0..4) {
            val random = (0..99).random()
            listRandN.add(random)
        }
        GlobalScope.launch {
            val pe = viewModel.getFiveMemes(listRandN)
            Log.d(TAG, "This is pe: $pe")
        }
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

    // just init recycle View
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
    // function get mem with index
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

    override fun onDestroy() {
        super.onDestroy()
        animatorObject.cancel()
    }


    private fun hideHeadBar() {
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }


}