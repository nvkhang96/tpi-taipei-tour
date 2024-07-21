package com.nvkhang96.tpitaipeitravel.attraction.presentation.attraction_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nvkhang96.tpitaipeitravel.R
import com.nvkhang96.tpitaipeitravel.attraction.domain.model.AttractionImage

class AttractionImageAdapter(
    private var images: List<AttractionImage>
) : RecyclerView.Adapter<AttractionImageAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(image: AttractionImage) {
            val ivImage = itemView.findViewById<ImageView>(R.id.ivImage)
            ivImage.load(image.src)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_attraction_image,
            parent,
            false,
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    fun updateImages(images: List<AttractionImage>) {
        this.images = ArrayList(images)
        notifyDataSetChanged()
    }
}