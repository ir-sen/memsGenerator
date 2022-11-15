package com.kis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kis.databinding.ActivityMainBinding
import com.kis.retrofit.MemsApiRetro
import com.kis.retrofit.RetroRequestT
import com.kis.viewModels.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import okhttp3.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    private final val TAG = "MainViewModelTAG"
    // add coroutine
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)


    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // example from geek

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        var answser = ""
        val deffRetro: Deferred<String> = scope.async {
            answser = viewModel.getOneMemes()
            answser
        }

        var logAnswer = runBlocking {
            deffRetro.await()
        }
        Log.d(TAG, logAnswer)
        Picasso.with(this)
            .load(logAnswer)
            .into(binding.imageView)



    }



}