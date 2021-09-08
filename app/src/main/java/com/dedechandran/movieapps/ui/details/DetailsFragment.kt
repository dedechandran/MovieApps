package com.dedechandran.movieapps.ui.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dedechandran.core.utils.convertDuration
import com.dedechandran.core.utils.getYear
import com.dedechandran.core.wrapper.Resource
import com.dedechandran.movieapps.R
import com.dedechandran.movieapps.databinding.FragmentDetailsBinding
import com.dedechandran.movieapps.ui.BaseFragmentBinding
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

    private fun observeData() {
        vm.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    // Do nothing
                }
                is Resource.Success -> {
                    val data = state.data
                    data?.let {
                        with(binding) {
                            data.imageUrl?.let {
                                Glide.with(requireContext()).load(data.imageUrl)
                                    .into(ivMovieDetails)
                            }
                                ?: ivMovieDetails.setImageResource(R.drawable.ic_baseline_broken_image_24)
                            tvToolbarTitle.text = data.title ?: DEFAULT_DETAILS
                            tvSynopsisValue.text = data.overview ?: NO_DATA
                            tvDurationValue.text = data.runtime?.convertDuration() ?: NO_DATA
                            tvReleaseYearValue.text = data.releaseDate?.getYear() ?: NO_DATA
                            tvStatusValue.text = data.status ?: NO_DATA
                            tvVoteAvgValue.text = data.voteAverage?.toString() ?: NO_DATA
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
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.something_went_wrong_remark),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    companion object {
        const val MOVIE_ID_EXTRAS = "MOVIE_ID_EXTRAS"
        private const val DEFAULT_MOVIE_ID = "0"
        private const val NO_DATA = "-"
        private const val DEFAULT_DETAILS = "Details"
    }
}