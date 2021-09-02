package com.dedechandran.movieapps.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator
import com.dedechandran.core.wrapper.Resource
import com.dedechandran.movieapps.BaseFragmentBinding
import com.dedechandran.movieapps.ui.details.DetailsFragment.Companion.MOVIE_ID_EXTRAS
import com.dedechandran.movieapps.favorite.databinding.FragmentFavoriteBinding
import com.dedechandran.movieapps.favorite.di.DaggerFavoriteComponent
import com.dedechandran.movieapps.di.FavoriteDependencies
import com.dedechandran.movieapps.favorite.di.ViewModelFactory
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : BaseFragmentBinding<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var vm: FavoriteMovieViewModel

    override fun initializeViewBinding(view: View): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.bind(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext().applicationContext,
                    FavoriteDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        vm = ViewModelProvider(this, viewModelFactory)[FavoriteMovieViewModel::class.java]
        binding.rvFavoriteMovie.apply {
            setOnFavoriteClickListener { id, isFavorite ->
                vm.onFavoriteIconClicked(id, isFavorite)
            }
            setOnItemClickListener {
                val args = bundleOf(MOVIE_ID_EXTRAS to it)
                navController.navigate(R.id.action_favoriteFragment_to_favoriteDetails, args)
            }
            setHasFixedSize(true)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        binding.ivArrowBack.setOnClickListener {
            navController.popBackStack()
        }
        return binding.root
    }

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
                        binding.rvFavoriteMovie.setItems(it)
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