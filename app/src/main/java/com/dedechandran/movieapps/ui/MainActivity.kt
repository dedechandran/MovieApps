package com.dedechandran.movieapps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.dedechandran.movieapps.R
import com.dedechandran.movieapps.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vm by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm.connectionState.observe(this) { isHasConnectivity ->
            if(isHasConnectivity) return@observe
            Snackbar.make(binding.root,resources.getString(R.string.no_network_connection_message),Snackbar.LENGTH_SHORT).show()
        }
    }
}