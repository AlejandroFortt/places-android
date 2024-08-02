package com.fortatic.apps.places.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fortatic.apps.places.data.model.HomePlace
import com.fortatic.apps.places.databinding.ItemPlacesBinding

class HomePlaceAdapter(
    private val places: List<HomePlace>
) : RecyclerView.Adapter<HomePlaceAdapter.HomePlaceViewHolder>() {

    class HomePlaceViewHolder(private val binding: ItemPlacesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: HomePlace) {
            binding.placeImage.load(place.imageUrl)
            binding.placeName.text = place.name.plus(", ")
            binding.placeCity.text = place.city
            binding.tvRate.text = place.rate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePlaceViewHolder {
        val binding = ItemPlacesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomePlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePlaceViewHolder, position: Int) = holder.bind(places[position])

    override fun getItemCount() = places.size
}