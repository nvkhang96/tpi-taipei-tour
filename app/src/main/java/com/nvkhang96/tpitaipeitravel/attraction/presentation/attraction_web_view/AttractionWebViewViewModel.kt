package com.nvkhang96.tpitaipeitravel.attraction.presentation.attraction_web_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvkhang96.tpitaipeitravel.core.domain.repository.KeyValueRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AttractionWebViewViewModel(
    private val keyValueRepository: KeyValueRepository,
): ViewModel() {

    data class State(
        val url: String = "",
    )

    sealed class Event {
        data object InvalidUrl : Event()
    }

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _event = Channel<Event>()
    val event = _event.receiveAsFlow()

    init {
        loadSelectedAttractionUrl()
    }

    private fun loadSelectedAttractionUrl() {
        viewModelScope.launch {
            val url = keyValueRepository.getString(KeyValueRepository.KEY_SELECTED_URL).first() ?: ""

            if (url.isBlank()) {
                _event.send(Event.InvalidUrl)
                return@launch
            }

            _state.update { state ->
                state.copy(url = url)
            }
        }
    }
}