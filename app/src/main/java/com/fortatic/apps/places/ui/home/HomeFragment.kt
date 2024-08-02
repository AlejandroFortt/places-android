package com.fortatic.apps.places.ui.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.fortatic.apps.places.databinding.FragmentHomeBinding
import com.fortatic.apps.places.ui.BaseFragment
import com.fortatic.apps.places.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var viewPager2: ViewPager2

    override fun onViewCreated() {
        viewPager2 = binding.placesPager

        val homePlaceAdapter = HomePlaceAdapter { placeId ->
            navigateToDetail(placeId)
        }
        viewPager2.adapter = homePlaceAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.places.collect {
                    Timber.d("places from home: $it")
                    when(it) {
                        is Result.Success -> {
                            homePlaceAdapter.submitList(it.data)
                        }
                        else -> {
                            Timber.e("Error loading places")
                        }
                    }
                }
            }
        }

        setupViewPager()
    }

    private fun setupViewPager() {
        viewPager2.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = ViewPager2.OVER_SCROLL_NEVER
        }

        val pagerTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(0))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.95f + r * 0.05f
            }
        }

        viewPager2.setPageTransformer(pagerTransformer)
    }

    private fun navigateToDetail(placeId: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(placeId)
        findNavController().navigate(action)
    }

}