package com.dedechandran.movieapps.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import com.dedechandran.core.wrapper.Resource
import com.dedechandran.movieapps.BaseFragmentBinding
import com.dedechandran.movieapps.R
import com.dedechandran.movieapps.ui.details.DetailsFragment.Companion.MOVIE_ID_EXTRAS
import com.dedechandran.movieapps.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class HomeFragment : BaseFragmentBinding<FragmentHomeBinding>(R.layout.fragment_home) {

    private val vm: HomeViewModel by viewModels()

    override fun initializeViewBinding(view: View): FragmentHomeBinding {
        return FragmentHomeBinding.bind(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.rvPopularMovie.apply {
            setOnFavoriteClickListener { id, isFavorite ->
                vm.onFavoriteIconClicked(id = id, isFavorite = isFavorite)
            }
            setOnItemClickListener {
                val args = bundleOf(MOVIE_ID_EXTRAS to it)
                navController.navigate(R.id.action_homeFragment_to_detailsFragment, args)
            }
            setHasFixedSize(true)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        binding.tvToolbarTitle.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
        return binding.root
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.initialize()
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
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}