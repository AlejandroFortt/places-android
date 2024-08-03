package com.fortatic.apps.places.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fortatic.apps.places.R
import com.fortatic.apps.places.data.model.Place
import com.fortatic.apps.places.databinding.FragmentDetailBinding
import com.fortatic.apps.places.ui.BaseFragment
import com.fortatic.apps.places.util.Result
import com.fortatic.apps.places.util.applyIfNotNull
import com.fortatic.apps.places.util.generateMarker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
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
                launch {
                    detailViewModel.placeData.collectLatest { result ->
                        when(result) {
                            is Result.Error -> handleLoadingError(result.exception)
                            is Result.Loading -> {}
                            is Result.Success -> handleSuccess(result.data)
                        }
                    }
                }
                launch {
                    detailViewModel.mapUiState.collectLatest { mapUiState ->
                        when (mapUiState) {
                            is MapUiState.UpdateMarker -> updateMap(mapUiState)
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    private fun handleLoadingError(exception: Exception) {
        Toast.makeText(
            context,
            getString(R.string.error_loading_place),
            Toast.LENGTH_LONG
        ).show()
        Timber.e(exception)
        findNavController().popBackStack()
    }

    private fun handleSuccess(data: Place) {
        setUiWithData(data)
        detailViewModel.setMapData(data)
    }

    private fun setUiWithData(data: Place) {
        binding.tvName.text = data.name
        binding.tvCountry.text = data.country
        binding.tvRate.text = data.rate
        binding.tvPrice.text = data.price
        binding.tvDescription.text = data.description
        Glide.with(requireContext())
            .load(data.imageUrl)
            .into(binding.placeImage)
    }

    private fun updateMap(mapUiState: MapUiState.UpdateMarker) {
        val position = LatLng(mapUiState.latitude.toDouble(), mapUiState.longitude.toDouble())
        val iconMap = requireContext().generateMarker(mapUiState.name)
        binding.mapView.getMapAsync { googleMap ->
            googleMap.addMarker {
                position(position)
                icon(iconMap)
            }
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    position,
                    resources.getDimension(R.dimen.zoom_dimension)
                )
            )
            googleMap.setOnMapClickListener {
                navigateToMapFragment(mapUiState.latitude, mapUiState.longitude, mapUiState.name)
            }
        }
    }

    private fun navigateToMapFragment(latitude: String, longitude: String, name: String) {
        val action = DetailFragmentDirections.actionDetailFragmentToMapFragment(
            latValue = latitude,
            lonValue = longitude,
            placeName = name
        )
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.applyIfNotNull { onCreate(savedInstanceState) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        binding.mapView.applyIfNotNull { onSaveInstanceState(outState) }
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.applyIfNotNull { onResume() }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.applyIfNotNull { onStart() }
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.applyIfNotNull { onStop() }
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.applyIfNotNull { onPause() }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.applyIfNotNull { onLowMemory() }
    }
}