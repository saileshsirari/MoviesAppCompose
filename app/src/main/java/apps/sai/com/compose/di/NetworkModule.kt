package apps.sai.com.compose.di

import android.content.Context
import androidx.room.Room
import apps.sai.com.movieapp.api.ApiKey
import apps.sai.com.movieapp.api.MovieApi
import apps.sai.com.movieapp.db.AppDatabase
import apps.sai.com.movieapp.db.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideMovieService(@ApiKey apiKey: String): MovieApi {
        return MovieApi.create(apiKey)
    }

    @Singleton
    @Provides
    @ApiKey
    fun provideApiKey():String {
        return "0e7274f05c36db12cbe71d9ab0393d47"
    }

    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "MovieDb"
        ).build()
    }
}
