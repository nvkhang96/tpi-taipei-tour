package com.nvkhang96.tpitaipeitravel.attraction.di

import com.nvkhang96.tpitaipeitravel.attraction.presentation.attraction_detail.AttractionDetailViewModel
import com.nvkhang96.tpitaipeitravel.attraction.presentation.attraction_list.AttractionListViewModel
import com.nvkhang96.tpitaipeitravel.attraction.presentation.attraction_web_view.AttractionWebViewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val attractionViewModelModule = module {
    viewModel {
        AttractionListViewModel(get(), get())
    }
    viewModel {
        AttractionDetailViewModel(get())
    }
    viewModel {
        AttractionWebViewViewModel(get())
    }
}