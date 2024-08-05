package apps.sai.com.movieapp.db

import androidx.paging.PagingSource
import androidx.room.*
import apps.sai.com.movieapp.data.Genre
import apps.sai.com.movieapp.data.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * FROM Movie")
    fun getAll(): PagingSource<Int, Movie>

    @Query("SELECT * FROM Movie where id = :id")
    fun getFav(id: Int): Flow<Movie?>

    @Query("SELECT * FROM Genre")
    suspend fun getAllGenres(): List<Genre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGenre(genres: List<Genre>)
}
