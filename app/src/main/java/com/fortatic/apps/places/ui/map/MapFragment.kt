package com.fortatic.apps.places.ui.map

import androidx.navigation.fragment.navArgs
import com.fortatic.apps.places.R
import com.fortatic.apps.places.databinding.FragmentMapBinding
import com.fortatic.apps.places.ui.BaseFragment
import com.fortatic.apps.places.util.generateMarker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate) {

    private val args: MapFragmentArgs by navArgs()

    override fun onViewCreated() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        val smallMarkerIcon = requireContext().generateMarker(args.placeName)
        mapFragment?.getMapAsync {
            val location = LatLng(args.latValue.toDouble(), args.lonValue.toDouble())
            it.addMarker(MarkerOptions().position(location).icon(smallMarkerIcon))
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(location, resources.getDimension(R.dimen.zoom_dimension_complete)))
        }
    }
}