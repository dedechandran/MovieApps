package com.dedechandran.movieapps.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import com.dedechandran.core.wrapper.Resource
import com.dedechandran.movieapps.R
import com.dedechandran.movieapps.databinding.FragmentHomeBinding
import com.dedechandran.movieapps.ui.BaseFragmentBinding
import com.dedechandran.movieapps.ui.details.DetailsFragment.Companion.MOVIE_ID_EXTRAS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class HomeFragment : BaseFragmentBinding<FragmentHomeBinding>(R.layout.fragment_home) {

    private val vm: HomeViewModel by viewModels()

    override fun initializeViewBinding(view: View): FragmentHomeBinding {
        return FragmentHomeBinding.bind(view)
    }

    @FlowPreview
    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        vm.initialize()
        observeData()
    }

    private fun setupUi() {
        binding.rvPopularMovie.apply {
            setOnItemClickListener {
                val args = bundleOf(MOVIE_ID_EXTRAS to it)
                navController.navigate(R.id.action_homeFragment_to_detailsFragment, args)
            }
            setHasFixedSize(true)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        binding.ivFavoriteIcon.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
    }

    private fun observeData() {
        vm.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    state.data?.let {
                        binding.rvPopularMovie.setItems(it)
                    }
                    binding.tvResultRemark.apply {
                        isVisible = state.data?.isNullOrEmpty() ?: false
                        text = resources.getString(R.string.empty_result_remark)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvResultRemark.apply {
                        visibility = View.VISIBLE
                        text = resources.getString(R.string.something_went_wrong_remark)
                    }
                }
            }
        }
    }

}