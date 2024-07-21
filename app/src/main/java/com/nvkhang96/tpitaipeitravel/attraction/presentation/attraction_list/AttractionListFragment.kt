package com.nvkhang96.tpitaipeitravel.attraction.presentation.attraction_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.nvkhang96.tpitaipeitravel.R
import com.nvkhang96.tpitaipeitravel.core.domain.enums.Language
import com.nvkhang96.tpitaipeitravel.databinding.FragmentAttractionListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.nvkhang96.tpitaipeitravel.attraction.presentation.attraction_list.AttractionListViewModel.Intent

class AttractionListFragment : Fragment() {

    private var binding: FragmentAttractionListBinding? = null
    private val viewModel: AttractionListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttractionListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        val attractionAdapter = AttractionPagingAdapter(
            onItemClick = { attraction ->
                viewModel.onIntent(Intent.TapOnAttraction(attraction))
            }
        )
        binding?.rvAttractions?.adapter = attractionAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                attractionAdapter.loadStateFlow.collect { loadStates ->
                    binding?.progressIndicator?.isVisible =
                        loadStates.refresh is LoadState.Loading
                                || loadStates.append is LoadState.Loading
                }
            }
        }

        observeState(attractionAdapter)

        observeEvents()
    }

    private fun setupToolbar() {
        binding?.toolbar?.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.change_language -> {
                    handleTapOnChangeLanguage()
                }
            }

            true
        }
    }

    private fun handleTapOnChangeLanguage() {
        val languageItems = resources.getStringArray(R.array.languages_array)
        val context = context ?: return

        AlertDialog.Builder(context)
            .setItems(languageItems) { _, which ->
                viewModel.onIntent(Intent.ChangeLanguage(Language.entries[which]))
            }
            .create()
            .show()
    }

    private fun observeState(
        attractionAdapter: AttractionPagingAdapter,
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    attractionAdapter.submitData(state.attractions)
                }
            }
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event) {
                        is AttractionListViewModel.Event.SelectedAttractionSaved -> {
                            findNavController().navigate(R.id.attraction_detail)
                        }

                        is AttractionListViewModel.Event.LanguageChanged -> {
                            changeAppLocale(event.language.getAndroidLocaleCode())
                        }
                    }
                }
            }
        }
    }

    private fun changeAppLocale(locale: String) {
        val appLocale = LocaleListCompat.forLanguageTags(locale)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}