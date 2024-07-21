package com.nvkhang96.tpitaipeitravel.attraction.presentation.attraction_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nvkhang96.tpitaipeitravel.attraction.domain.model.Attraction
import com.nvkhang96.tpitaipeitravel.databinding.ListItemAttractionBinding

class AttractionPagingAdapter(
    diffCallback: DiffUtil.ItemCallback<Attraction> = AttractionComparator,
    private val onItemClick: (Attraction) -> Unit = {},
) : PagingDataAdapter<Attraction, AttractionPagingAdapter.ViewHolder>(diffCallback) {
    class ViewHolder(private val binding: ListItemAttractionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(attraction: Attraction, onItemClick: (Attraction) -> Unit) {
            with(binding) {
                itemLayout.setOnClickListener {
                    onItemClick(attraction)
                }

                attraction.images.firstOrNull()?.src?.let { imageSrc ->
                    ivImage.load(imageSrc)
                }

                tvName.text = attraction.name
                tvIntroduction.text = attraction.introduction
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { attraction ->
            holder.bind(attraction, onItemClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemAttractionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false,
        )
        return ViewHolder(binding)
    }
}

object AttractionComparator : DiffUtil.ItemCallback<Attraction>() {
    override fun areItemsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
        return oldItem == newItem
    }
}