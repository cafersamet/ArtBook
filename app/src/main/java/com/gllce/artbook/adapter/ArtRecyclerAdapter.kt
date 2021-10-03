package com.gllce.artbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gllce.artbook.databinding.ArtItemBinding
import com.gllce.artbook.roomdb.Art
import com.gllce.artbook.util.ItemClickListener
import com.gllce.artbook.view.ArtFragmentDirections
import com.gllce.artbook.viewmodel.ArtViewModel

class ArtRecyclerAdapter : RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>() {

    class ArtViewHolder(private val binding: ArtItemBinding) :
        RecyclerView.ViewHolder(binding.root), ItemClickListener {
        fun bind(item: Art?) {
            binding.art = item
            binding.clickListener = this
        }

        companion object {
            fun create(view: ViewGroup): ArtViewHolder {
                val inflater = LayoutInflater.from(view.context)
                val binding = ArtItemBinding.inflate(inflater, view, false)
                return ArtViewHolder(binding)
            }
        }

        override fun onClicked(view: View, id: Int) {
            val action = ArtFragmentDirections.actionArtFragmentToArtDetailsFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

    }

    private val listDiffer = AsyncListDiffer(this, diffUtil)

    var arts: List<Art>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        return ArtViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val art = arts[position]
        holder.bind(art)
    }

    override fun getItemCount(): Int {
        return arts.size
    }
}