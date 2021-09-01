package com.dedechandran.movieapps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dedechandran.core.utils.convertDuration
import com.dedechandran.core.utils.getYear
import com.dedechandran.core.wrapper.Resource
import com.dedechandran.movieapps.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val vm by viewModels<DetailsViewModel>()
    private val navController by lazy {
        findNavController()
    }

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
        setListener(args)
        vm.initialize(args.toInt())
        vm.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val data = state.data
                    data?.let {
                        with(binding) {
                            Glide.with(requireContext()).load(data.imageUrl).into(ivMovieDetails)
                            tvToolbarTitle.text = data.title
                            tvSynopsisValue.text = data.overview
                            tvDurationValue.text = data.runtime.convertDuration()
                            tvReleaseYearValue.text = data.releaseDate.getYear()
                            tvStatusValue.text = data.status
                            tvVoteAvgValue.text = data.voteAverage.toString()
                            val favoriteIcon = if (it.isFavorite) {
                                R.drawable.ic_baseline_favorite_24
                            } else {
                                R.drawable.ic_baseline_favorite_border_24
                            }
                            ivFavoriteIcon.setImageResource(favoriteIcon)
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setListener(args: String) {
        binding.ivFavoriteIcon.setOnClickListener {
            vm.onFavoriteIconClicked(args)
        }
        binding.ivArrowBack.setOnClickListener {
            navController.popBackStack()
        }
    }
}