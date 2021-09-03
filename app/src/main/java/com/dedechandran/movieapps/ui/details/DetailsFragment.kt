package com.dedechandran.movieapps.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dedechandran.core.utils.convertDuration
import com.dedechandran.core.utils.getYear
import com.dedechandran.core.wrapper.Resource
import com.dedechandran.movieapps.BaseFragmentBinding
import com.dedechandran.movieapps.R
import com.dedechandran.movieapps.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragmentBinding<FragmentDetailsBinding>(R.layout.fragment_details) {

    private val vm by viewModels<DetailsViewModel>()

    override fun initializeViewBinding(view: View): FragmentDetailsBinding {
        return FragmentDetailsBinding.bind(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.getString(MOVIE_ID_EXTRAS) ?: DEFAULT_MOVIE_ID
        setListener(args)
        vm.initialize(args.toInt())
        observeData()
    }

    private fun setListener(args: String) {
        binding.ivFavoriteIcon.setOnClickListener {
            vm.onFavoriteIconClicked(args)
        }
        binding.ivArrowBack.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun observeData(){
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

    companion object {
        const val MOVIE_ID_EXTRAS = "MOVIE_ID_EXTRAS"
        private const val DEFAULT_MOVIE_ID = "0"
    }
}