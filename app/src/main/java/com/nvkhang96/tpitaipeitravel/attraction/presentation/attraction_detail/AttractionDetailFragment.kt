package com.nvkhang96.tpitaipeitravel.attraction.presentation.attraction_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.FullScreenCarouselStrategy
import com.nvkhang96.tpitaipeitravel.R
import com.nvkhang96.tpitaipeitravel.databinding.FragmentAttractionDetailBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.nvkhang96.tpitaipeitravel.attraction.presentation.attraction_detail.AttractionDetailViewModel.Intent

class AttractionDetailFragment : Fragment() {

    private var binding: FragmentAttractionDetailBinding? = null
    private val viewModel: AttractionDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttractionDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.toolbar?.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        val imagesAdapter = AttractionImageAdapter(emptyList())
        setupImagesCarousel(imagesAdapter)

        observeState(imagesAdapter)

        observeEvents()
    }

    private fun setupImagesCarousel(
        imagesAdapter: AttractionImageAdapter,
    ) {
        val rvLayoutManager = CarouselLayoutManager(FullScreenCarouselStrategy())
        binding?.rvImages?.apply {
            adapter = imagesAdapter
            layoutManager = rvLayoutManager
            CarouselSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun observeState(
        imagesAdapter: AttractionImageAdapter,
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    val binding = binding ?: return@collectLatest

                    with(binding) {
                        imagesAdapter.updateImages(
                            state.attraction?.images
                                ?.filter { it.src.isNotBlank() }
                                ?: emptyList()
                        )

                        toolbar.title = state.attraction?.name
                        tvName.text = state.attraction?.name
                        tvIntroduction.text = state.attraction?.introduction
                        tvAddress.text = state.attraction?.address
                        tvLastUpdatedTime.text = state.attraction?.modified

                        val url = state.attraction?.url
                        if (url?.isNotBlank() == true) {
                            tvUrl.text = url
                            tvUrl.setOnClickListener {
                                viewModel.onIntent(Intent.TapOnUrl(url))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event) {
                        is AttractionDetailViewModel.Event.InvalidUrl -> {
                            Toast.makeText(context, R.string.invalid_url, Toast.LENGTH_SHORT).show()
                        }
                        AttractionDetailViewModel.Event.NotFoundSelectedAttraction -> {
                            findNavController().popBackStack()
                        }
                        AttractionDetailViewModel.Event.SelectedUrlSaved -> {
                            findNavController().navigate(R.id.attraction_web_view)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}