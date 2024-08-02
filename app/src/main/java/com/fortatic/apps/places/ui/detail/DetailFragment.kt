package com.fortatic.apps.places.ui.detail

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fortatic.apps.places.databinding.FragmentDetailBinding
import com.fortatic.apps.places.ui.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    override fun onViewCreated() {
        lifecycleScope.launch {
            delay(1000)
            findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToMapFragment())
        }
    }
}