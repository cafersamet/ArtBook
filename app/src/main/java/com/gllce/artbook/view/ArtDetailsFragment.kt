package com.gllce.artbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gllce.artbook.R
import com.gllce.artbook.databinding.FragmentArtDetailsBinding
import com.gllce.artbook.util.Status
import com.gllce.artbook.util.downloadPath
import com.gllce.artbook.util.placeholderProgressBar
import com.gllce.artbook.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtDetailsFragment : Fragment(R.layout.fragment_art_details) {

    private var fragmentBinding: FragmentArtDetailsBinding? = null
    private val viewModel: ArtViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        binding.imageView.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.saveButton.setOnClickListener {
            viewModel.makeArt(
                binding.nameEditText.text.toString(),
                binding.artistEditText.text.toString(),
                binding.yearEditText.text.toString()
            )
        }
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.insertArtMessage.observe(viewLifecycleOwner, {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
                        viewModel.resetInsertArtMsg()
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })

        viewModel.selectedImageUrl.observe(viewLifecycleOwner, { url ->
            fragmentBinding?.imageView?.downloadPath(url, placeholderProgressBar(requireContext()))
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}