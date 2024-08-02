package com.fortatic.apps.places.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fortatic.apps.places.data.model.HomePlace
import com.fortatic.apps.places.databinding.ItemPlacesBinding

class HomePlaceDiffUtil: DiffUtil.ItemCallback<HomePlace>() {
    override fun areItemsTheSame(oldItem: HomePlace, newItem: HomePlace) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: HomePlace, newItem: HomePlace) = oldItem == newItem
}

class HomePlaceAdapter(
    private val onItemClick: (String) -> Unit
) : ListAdapter<HomePlace, HomePlaceAdapter.HomePlaceViewHolder>(HomePlaceDiffUtil()) {

    class HomePlaceViewHolder(
        private val binding: ItemPlacesBinding,
        private val onItemClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: HomePlace) {
            binding.placeImage.load(place.imageUrl)
            binding.placeName.text = place.name.plus(", ")
            binding.placeCity.text = place.city
            binding.tvRate.text = place.rate
            binding.root.setOnClickListener {
                onItemClick(place.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePlaceViewHolder {
        val binding = ItemPlacesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomePlaceViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: HomePlaceViewHolder, position: Int) = holder.bind(getItem(position))
}