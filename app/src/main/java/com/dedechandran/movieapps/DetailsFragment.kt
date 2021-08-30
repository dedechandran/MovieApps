package com.dedechandran.movieapps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.dedechandran.core.wrapper.UiState
import com.dedechandran.movieapps.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val vm by viewModels<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        binding = FragmentDetailsBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.getString("MOVIE_ID") ?: "0"
        vm.initialize(args.toInt())
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect { state ->
                    when (state) {
                        is UiState.Loading -> {

                        }
                        is UiState.Success -> {
                            val data = state.data
                            Glide.with(requireContext()).load(data.imageUrl)
                                .into(binding.ivMovieDetails)
                            binding.tvToolbarTitle.text = data.title
                            binding.tvSynopsisValue.text = data.overview
                            binding.tvDurationValue.text = data.runtime.toString()
                            binding.tvReleaseYearValue.text = "2020"
                            binding.tvRevenueValue.text = "10000"
                            binding.tvVoteAvgValue.text = data.voteAverage.toString()
                        }
                        is UiState.Error -> {
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}