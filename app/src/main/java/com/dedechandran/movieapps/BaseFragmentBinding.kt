package com.dedechandran.movieapps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragmentBinding<T : ViewBinding>(@LayoutRes val layoutInt: Int) : Fragment() {
    protected val navController by lazy {
        findNavController()
    }
    private var _binding: T? = null
    protected val binding: T
        get() = _binding as T

    abstract fun initializeViewBinding(view: View): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutInt, container, false)
        if (view != null) {
            _binding = initializeViewBinding(view)
        }
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}