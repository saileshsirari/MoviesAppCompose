package apps.sai.com.compose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import apps.sai.com.compose.model.MovieType
import apps.sai.com.movieapp.api.MovieRepository
import apps.sai.com.movieapp.data.GenreResponse
import apps.sai.com.movieapp.data.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * UI state for the Home screen
 */
sealed interface MovieUiState {
    data class Success(
        val movieTypes: Map<MovieType, Flow<PagingData<Movie>>> = emptyMap(),
        val currentMovieType: MovieType = MovieType.NOW_PLAYING,
        val isShowingHomepage: Boolean = true
    ) : MovieUiState {
        val currentSelectedMovies: Flow<PagingData<Movie>> by lazy {
            movieTypes[currentMovieType] ?: emptyFlow()
        }
    }

    data object Error : MovieUiState
    data object Loading : MovieUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    private val popular = movieRepository.popular()
    private val nowPlaying = movieRepository.nowPlaying()
    private val topRated = movieRepository.topRated()
    private val upcoming = movieRepository.upcoming()
    private val _movieUiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val movieUiState = _movieUiState.asStateFlow()

    init {
        val movieTypes: MutableMap<MovieType, Flow<PagingData<Movie>>> = mutableMapOf()
        movieTypes[MovieType.NOW_PLAYING] = nowPlaying
        movieTypes[MovieType.POPULAR] = popular
        movieTypes[MovieType.TOP_RATED] = topRated
        movieTypes[MovieType.UPCOMING] = upcoming
        _movieUiState.value =
            MovieUiState.Success(movieTypes = movieTypes, currentMovieType = MovieType.NOW_PLAYING)
    }

    fun updateCurrentMovieType(movieType: MovieType) {
        (_movieUiState.value as MovieUiState.Success).let { uiState ->
            _movieUiState.update {
                uiState.copy(currentMovieType = movieType, isShowingHomepage = true)
            }
        }
    }
}

