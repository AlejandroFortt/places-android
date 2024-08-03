package com.fortatic.apps.places.ui.detail

import android.os.Bundle
import android.view.View
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
import com.fortatic.apps.places.util.applyIfNotNull
import com.fortatic.apps.places.util.generateMarker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val detailViewModel: DetailViewModel by viewModels()

    private var mMap: MapView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMap?.applyIfNotNull { onCreate(savedInstanceState) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mMap?.applyIfNotNull { onSaveInstanceState(outState) }
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mMap?.applyIfNotNull { onResume() }
    }

    override fun onStart() {
        super.onStart()
        mMap?.applyIfNotNull { onStart() }
    }

    override fun onStop() {
        super.onStop()
        mMap?.applyIfNotNull { onStop() }
    }

    override fun onPause() {
        super.onPause()
        mMap?.applyIfNotNull { onPause() }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMap?.applyIfNotNull { onLowMemory() }
    }

    override fun onViewCreated() {

        mMap = binding.mapView

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

        val (latitude, longitude) = data.coordinates.split(",")
        val iconMap = requireContext().generateMarker(data.name)
        setMapView(latitude, longitude, iconMap)
    }

    private fun setMapView(latitude: String, longitude: String, iconMap: BitmapDescriptor) {
        val position = LatLng(latitude.toDouble(), longitude.toDouble())
        binding.mapView.getMapAsync {
            it.addMarker {
                position(position)
                icon(iconMap)
            }
            it.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    position,
                    resources.getDimension(R.dimen.zoom_dimension)
                )
            )
        }
    }
}