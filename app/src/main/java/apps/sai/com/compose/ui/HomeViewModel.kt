package apps.sai.com.compose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import apps.sai.com.movieapp.api.MovieRepository
import apps.sai.com.movieapp.data.GenreResponse
import apps.sai.com.movieapp.data.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

/**
 * UI state for the Home screen
 */
sealed interface MovieUiState {
    data class Success(val movies: Flow<PagingData<Movie>>) : MovieUiState
    data object Error : MovieUiState
    data object Loading : MovieUiState
}
@HiltViewModel
class HomeViewModel  @Inject constructor(private val movieRepository: MovieRepository) : ViewModel()  {

    val popular = movieRepository.popular()
    val nowPlaying = movieRepository.nowPlaying()
    val topRated = movieRepository.topRated()
    val upcoming = movieRepository.upcoming()

}