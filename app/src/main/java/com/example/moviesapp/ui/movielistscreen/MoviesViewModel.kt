package com.example.moviesapp.ui.movielistscreen


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
class MoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesUiState())
    var uiState: StateFlow<MoviesUiState> = _uiState

    private val moviesListExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _: CoroutineContext, throwable: Throwable ->
            run {
                if (throwable is UnknownHostException) {
                    _uiState.update { it.copy(error = "Internet is not available.", isRefreshing = false, isLoading = false) }
                } else {
                    _uiState.update { it.copy(error = "Unable to fetch movies (cause: " + throwable.message + ")", isRefreshing = false, isLoading = false) }
                }
            }
        }

    init {
        fetchMovies(null)
    }

    fun fetchMovies(page: Int?) {
        viewModelScope.launch(moviesListExceptionHandler) {
            if (!_uiState.value.isLoading) {
                _uiState.update { it.copy(isLoading = true) }
                val result = moviesRepository.fetchMovies(page ?: (_uiState.value.currentPage + 1))
                result.catch { e ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            error = e.message ?: "Unknown error"
                        )
                    }
                }
                    .collect {
                        if (it.hasNoItems()) {
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    error = "No movies found"
                                )
                            }

                        } else {
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    data = if (it.getCurrentPage() == 1){
                                        it.getItems()
                                    } else  {
                                        state.data.plus(it.getItems())
                                    },
                                    error = null,
                                    currentPage = it.getCurrentPage()
                                )
                            }
                        }
                    }
            }
        }
    }

    fun refresh() {
        fetchMovies(1)
    }
}

data class MoviesUiState (
    var isLoading: Boolean = false,
    var isRefreshing: Boolean = false,
    val error: String? = null,
    val data: List<Movie> = emptyList(),
    val currentPage: Int = 0
)
