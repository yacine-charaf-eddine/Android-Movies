package com.example.moviesapp.ui.moviesscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.MoviesRepository
import com.example.moviesapp.data.api.Movie
import com.example.moviesapp.ui.moviesscreen.MoviesUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    var uiState: StateFlow<MoviesUiState> = _uiState
    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _uiState.value = MoviesUiState.Loading
            val result = moviesRepository.fetchMovies()
            result.catch { e ->
                    _uiState.value = MoviesUiState.Error(e.message ?: "Unknown error")
                }
                .collect {
                    if (it.hasNoItems()) {
                        _uiState.value = MoviesUiState.Error("No movies found")
                    } else{
                        _uiState.value = Success(it.getItems())
                    }
                }
        }
    }

/*    val uiState: StateFlow<MoviesUiState> = moviesRepository
        .fetchMovies().map<List<String>, MoviesUiState>(::Success)
        .catch { emit(MoviesUiState.Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MoviesUiState.Loading)*/

}

sealed interface MoviesUiState {
    data object Loading : MoviesUiState
    data class Error(val message: String) : MoviesUiState
    data class Success(val data: List<Movie>) : MoviesUiState
}