package com.nvkhang96.tpitaipeitravel.attraction.presentation.attraction_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nvkhang96.tpitaipeitravel.attraction.domain.model.Attraction
import com.nvkhang96.tpitaipeitravel.attraction.domain.model.AttractionQuery
import com.nvkhang96.tpitaipeitravel.attraction.domain.repository.AttractionRepository
import com.nvkhang96.tpitaipeitravel.core.domain.enums.Language
import com.nvkhang96.tpitaipeitravel.core.domain.repository.KeyValueRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AttractionListViewModel(
    private val attractionRepository: AttractionRepository,
    private val keyValueRepository: KeyValueRepository,
) : ViewModel() {

    data class State(
        val attractions: PagingData<Attraction> = PagingData.empty(),
    )

    sealed class Intent {
        data class TapOnAttraction(val attraction: Attraction) : Intent()
        data class ChangeLanguage(val language: Language) : Intent()
    }

    sealed class Event {
        data object SelectedAttractionSaved : Event()
        data class LanguageChanged(val language: Language) : Event()
    }

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _event = Channel<Event>()
    val event = _event.receiveAsFlow()

    private var _getAttractionsJob: Job? = null

    init {
        viewModelScope.launch {
            val language = Language.fromValue(
                keyValueRepository.getString(KeyValueRepository.KEY_LANGUAGE).first()
            ) ?: Language.en
            val query = AttractionQuery(language = language.getApiPath())
            getAllAttractions(query)
        }
    }

    private fun getAllAttractions(query: AttractionQuery) {
        _getAttractionsJob?.cancel()
        _getAttractionsJob = viewModelScope.launch {
            attractionRepository.getAllAttractions(query)
                .cachedIn(viewModelScope)
                .collect { attractions ->
                    _state.update { state ->
                        state.copy(
                            attractions = attractions,
                        )
                    }
                }
        }
    }

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.TapOnAttraction -> {
                handleTapOnAttraction(intent.attraction)
            }

            is Intent.ChangeLanguage -> {
                handleChangeLanguage(intent.language)
            }
        }
    }

    private fun handleTapOnAttraction(attraction: Attraction) {
        viewModelScope.launch {
            val attractionJson = Json.encodeToString(attraction)
            keyValueRepository.putString(KeyValueRepository.KEY_SELECTED_ATTRACTION, attractionJson)
            _event.send(Event.SelectedAttractionSaved)
        }
    }

    private fun handleChangeLanguage(newLanguage: Language) {
        viewModelScope.launch {
            val currentLanguage = Language.fromValue(
                keyValueRepository.getString(KeyValueRepository.KEY_LANGUAGE).first()
            ) ?: Language.en

            if (currentLanguage == newLanguage) {
                return@launch
            }

            keyValueRepository.putString(KeyValueRepository.KEY_LANGUAGE, newLanguage.value)
            _event.send(Event.LanguageChanged(newLanguage))

            val query = AttractionQuery(language = newLanguage.getApiPath())
            getAllAttractions(query)
        }
    }
}