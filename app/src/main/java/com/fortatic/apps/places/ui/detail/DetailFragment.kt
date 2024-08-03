package com.fortatic.apps.places.ui.detail

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.fortatic.apps.places.R
import com.fortatic.apps.places.data.model.Place
import com.fortatic.apps.places.databinding.FragmentDetailBinding
import com.fortatic.apps.places.ui.BaseFragment
import com.fortatic.apps.places.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onViewCreated() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.placeData.collectLatest {
                    when(it) {
                        is Result.Error -> {
                            Toast.makeText(
                                context,
                                getString(R.string.error_loading_place),
                                Toast.LENGTH_LONG
                            ).show()
                            Timber.e(it.exception)
                            findNavController().popBackStack()
                        }
                        is Result.Loading -> {
                            //use shimmer effect
                        }

                        is Result.Success -> setUiWithData(it.data)
                    }
                }
            }
        }
    }

    private fun setUiWithData(data: Place) {
        Timber.d("Place data: $data")
        binding.tvName.text = data.name
        binding.tvCountry.text = data.country
        binding.tvRate.text = data.rate
        binding.tvPrice.text = data.price
        binding.tvDescription.text = data.description
        binding.placeImage.load(data.imageUrl)
    }
}