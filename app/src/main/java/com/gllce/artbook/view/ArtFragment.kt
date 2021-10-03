package com.gllce.artbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gllce.artbook.R
import com.gllce.artbook.adapter.ArtRecyclerAdapter
import com.gllce.artbook.databinding.FragmentArtsBinding
import com.gllce.artbook.roomdb.Art
import com.gllce.artbook.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtFragment : Fragment(R.layout.fragment_arts) {

    private var fragmentBinding: FragmentArtsBinding? = null
    private val viewModel: ArtViewModel by activityViewModels()
    private val artRecyclerAdapter: ArtRecyclerAdapter = ArtRecyclerAdapter()

    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPos = viewHolder.layoutPosition
            val selectedArt = artRecyclerAdapter.arts[layoutPos]
            viewModel.deleteArt(selectedArt)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }

        binding.recyclerViewArt.apply {
            adapter = artRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.recyclerViewArt)
    }

    private fun subscribeToObservers(){
        viewModel.artList.observe(viewLifecycleOwner, {
            artRecyclerAdapter.arts = it
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}