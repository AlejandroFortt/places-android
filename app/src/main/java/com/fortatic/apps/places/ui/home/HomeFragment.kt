package com.fortatic.apps.places.ui.home

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fortatic.apps.places.databinding.FragmentHomeBinding
import com.fortatic.apps.places.ui.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated() {
        lifecycleScope.launch {
            delay(1000)
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment())
        }
    }

}