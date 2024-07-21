package com.nvkhang96.tpitaipeitravel.attraction.presentation.attraction_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvkhang96.tpitaipeitravel.attraction.domain.model.Attraction
import com.nvkhang96.tpitaipeitravel.core.domain.repository.KeyValueRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class AttractionDetailViewModel(
    private val keyValueRepository: KeyValueRepository,
) : ViewModel() {
    data class State(
        val attraction: Attraction? = null,
    )

    sealed class Intent {
        data class TapOnUrl(val url: String) : Intent()
    }

    sealed class Event {
        data object NotFoundSelectedAttraction : Event()
        data class InvalidUrl(val invalidUrl: String) : Event()
        data object SelectedUrlSaved : Event()
    }

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _event = Channel<Event>()
    val event = _event.receiveAsFlow()

    init {
        loadSelectedAttraction()
    }

    private fun loadSelectedAttraction() {
        viewModelScope.launch {
            val attractionJson =
                keyValueRepository.getString(KeyValueRepository.KEY_SELECTED_ATTRACTION).first()
            if (attractionJson == null) {
                _event.send(Event.NotFoundSelectedAttraction)
                return@launch
            }
            val attraction = Json.decodeFromString(Attraction.serializer(), attractionJson)
            _state.update { state ->
                state.copy(attraction = attraction)
            }
        }
    }

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.TapOnUrl -> {
                handleTapOnUrl(intent.url)
            }
        }
    }

    private fun handleTapOnUrl(url: String) {
        viewModelScope.launch {
            if (url.isBlank()) { // TODO: Should validate url
                _event.send(Event.InvalidUrl(url))
                return@launch
            }

            keyValueRepository.putString(KeyValueRepository.KEY_SELECTED_URL, url)
            _event.send(Event.SelectedUrlSaved)
        }
    }
}