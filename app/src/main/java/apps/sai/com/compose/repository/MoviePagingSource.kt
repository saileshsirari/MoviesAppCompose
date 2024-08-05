package apps.sai.com.compose.repository

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingSource
import androidx.paging.PagingState
import apps.sai.com.compose.model.MovieType
import apps.sai.com.movieapp.api.MovieApi
import apps.sai.com.movieapp.data.GenreResponse
import apps.sai.com.movieapp.data.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviePagingSource(
    private val movieApi: MovieApi,
    private val movieType: MovieType,
    private val query: String = "",
) : PagingSource<Int, Movie>() {

    // The refresh key is used for subsequent refresh calls to PagingSource.
    // load after the initial load
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    fun genres(): Flow<GenreResponse> = flow {
        movieApi.genres()
    }

   /* suspend fun genreResponse()  = lazy{
        movieApi.genres().genres
    }*/

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {

            /* val response = when (movieType) {
                 MovieType.NOW_PLAYING -> movieApi.nowPlaying(position)
                 MovieType.POPULAR -> movieApi.popular(position)
                 MovieType.TOP_RATED -> movieApi.topRated(position)
                 MovieType.UPCOMING -> movieApi.upcoming(position)
                 MovieType.SEARCH -> movieApi.search(position, query)
             }*/

            val response = movieApi.nowPlaying(position)
            val items = response.results
            val genreResponse = movieApi.genres().genres
            items.forEach { movie ->
                val list = movie.genres.toMutableList()
                genreResponse.filter { genre ->
                    movie.genreIds.contains(genre?.id)
                }.map {
                    if (movie.genres.isEmpty()) {
                        movie.genres = arrayListOf()
                    }
                    list.add(it)
                }
                movie.genres = list
            }

            val nextKey = if (items.isEmpty()) {
                null
            } else {
                position + 1
            }

            LoadResult.Page(
                data = items,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}