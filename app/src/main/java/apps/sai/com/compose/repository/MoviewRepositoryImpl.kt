package apps.sai.com.compose.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import apps.sai.com.compose.model.MovieType
import apps.sai.com.movieapp.api.MovieApi
import apps.sai.com.movieapp.api.MovieRepository
import apps.sai.com.movieapp.data.*
import apps.sai.com.movieapp.db.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl(private val api: MovieApi, private val movieDao: MovieDao) :
    MovieRepository {
    override fun nowPlaying(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { MoviePagingSource(api, MovieType.NOW_PLAYING) }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun popular(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { MoviePagingSource(api, MovieType.POPULAR) }
        ).flow
    }

    override fun topRated(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { MoviePagingSource(api, MovieType.TOP_RATED) }
        ).flow
    }

    override fun upcoming(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { MoviePagingSource(api, MovieType.UPCOMING) }
        ).flow
    }

    override fun search(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { MoviePagingSource(api, MovieType.SEARCH, query) }
        ).flow
    }

    override suspend fun genres(): Flow<GenreResponse> {
        val list = movieDao.getAllGenres()
        if(list.isNotEmpty()){
            return flow {
                emit(GenreResponse(ArrayList(list)))
            }
        }
        return flow {
            val genreResponse = api.genres()
            movieDao.insertAllGenre(genreResponse.genres)
            emit(genreResponse)
        }
    }

    override fun favouritesMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { movieDao.getAll() }
        ).flow
    }

    override fun movieDetails(id: Int): Flow<Movie> {
        return flow {
            emit(api.movieDetails(id))
        }
    }


    override fun isFavourite(id: Int): Flow<Movie?> {
        return movieDao.getFav(id)
    }

    override suspend fun favourite(movie: Movie) {
        movieDao.insertAll(listOf(movie))
    }

    override suspend fun removeFavourite(movie: Movie) {
        movieDao.delete(movie)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}