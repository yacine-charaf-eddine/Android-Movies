package com.example.moviesapp.ui.moviedetailscreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.source.MoviesRepository
import com.example.moviesapp.data.source.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    var uiState: StateFlow<MovieUiState> = _uiState

    private val movieDetailExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _: CoroutineContext, throwable: Throwable ->
            run {
                if (throwable is UnknownHostException) {
                    _uiState.update { it.copy(error = "Internet is not available.") }
                } else {
                    _uiState.update { it.copy(error = "Unable to fetch movies (cause: " + throwable.message + ")") }
                }
            }
        }
    fun fetchMovie(movieId: String) {
        viewModelScope.launch(movieDetailExceptionHandler) {
            val result = moviesRepository.fetchMovie(movieId)
            result.catch {
                _uiState.update {state ->
                    state.copy(
                        error = it.message ?: "Unknown error"
                    )
                }
            }
            .collect {
                if (it.hasNoItem()) {
                    _uiState.update {state ->
                        state.copy(
                            error = "No movie found"
                        )
                    }
                }else{
                    _uiState.update {state ->
                       state.copy(
                           data = it.getItem()
                       )
                   }
                }
            }
        }
    }


}

data class MovieUiState (
    val error: String? = null,
    val data: Movie? = null,
)