package com.gllce.artbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gllce.artbook.databinding.ImageItemBinding
import com.gllce.artbook.util.ImageClickListener

class ImageRecyclerAdapter(private val onItemClickListener: ImageClickListener) :
    RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {


    class ImageViewHolder(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root), ImageClickListener {

        fun bind(item: String?) {
            binding.imageUrl = item
            binding.clickListener = this
        }

        companion object {
            private var onItemClickListener: ImageClickListener? = null

            fun create(view: ViewGroup, clickListener: ImageClickListener): ImageViewHolder {
                val inflater = LayoutInflater.from(view.context)
                val binding = ImageItemBinding.inflate(inflater, view, false)
                onItemClickListener = clickListener
                return ImageViewHolder(binding)
            }
        }

        override fun onImageClick(imageUrl: String) {
            onItemClickListener?.onImageClick(imageUrl)
        }
    }


    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    private val listDiffer = AsyncListDiffer(this, diffUtil)

    var images: List<String>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.create(parent, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = images[position]
        holder.bind(imageUrl)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}