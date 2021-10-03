package com.gllce.artbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gllce.artbook.R
import com.gllce.artbook.adapter.ImageRecyclerAdapter
import com.gllce.artbook.databinding.FragmentImageApiBinding
import com.gllce.artbook.util.ImageClickListener
import com.gllce.artbook.util.Status
import com.gllce.artbook.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageApiFragment : Fragment(R.layout.fragment_image_api) {

    private val viewModel: ArtViewModel by activityViewModels()

    private var fragmentBinding: FragmentImageApiBinding? = null

    private lateinit var imageAdapter: ImageRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        imageAdapter = ImageRecyclerAdapter(onItemClickListener)

        var job: Job? = null

        binding.searchEditText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    val text = it.toString()
                    if (text.isNotEmpty()) {
                        viewModel.searchImage(text)
                    }
                }
            }
        }
        subscribeToObservers()

        binding.imageRecyclerView.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private val onItemClickListener = object : ImageClickListener {
        override fun onImageClick(imageUrl: String) {
            findNavController().popBackStack()
            viewModel.setSelectedImage(imageUrl)
        }
    }

    private fun subscribeToObservers() {
        viewModel.imageList.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL
                    }

                    imageAdapter.images = urls ?: listOf()

                    fragmentBinding?.progressBar?.visibility = View.GONE
                }
                Status.ERROR -> {
                    fragmentBinding?.progressBar?.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()

                }
                Status.LOADING -> {
                    fragmentBinding?.progressBar?.visibility = View.VISIBLE
                }
            }
        })
    }
}