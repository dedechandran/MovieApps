package com.dedechandran.movieapps.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dedechandran.movieapps.R
import com.dedechandran.movieapps.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vm by viewModels<MainViewModel>()
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        vm.connectionState.observe(this) { isHasConnectivity ->
            if (isHasConnectivity) return@observe
            binding?.root?.let {
                Snackbar.make(
                    it,
                    resources.getString(R.string.no_network_connection_message),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }
}