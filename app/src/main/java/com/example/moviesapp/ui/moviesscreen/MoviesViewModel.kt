package com.example.moviesapp.ui.moviesscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.MoviesRepository
import com.example.moviesapp.ui.moviesscreen.MoviesUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    val uiState: StateFlow<MoviesUiState> = moviesRepository
        .movies.map<List<String>, MoviesUiState>(::Success)
        .catch { emit(MoviesUiState.Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MoviesUiState.Loading)

}

sealed interface MoviesUiState {
    data object Loading : MoviesUiState
    data class Error(val throwable: Throwable) : MoviesUiState
    data class Success(val data: List<String>) : MoviesUiState
}