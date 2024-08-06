package apps.sai.com.compose.di

import apps.sai.com.compose.repository.MovieRepositoryImpl
import apps.sai.com.movieapp.api.MovieApi
import apps.sai.com.movieapp.api.MovieRepository
import apps.sai.com.movieapp.db.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {
    @Provides
    fun provideMovieRepository(api: MovieApi, movieDao: MovieDao): MovieRepository =
        MovieRepositoryImpl(api,movieDao)
}