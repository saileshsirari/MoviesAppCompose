package apps.sai.com.movieapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import apps.sai.com.movieapp.data.Genre
import apps.sai.com.movieapp.data.Movie

@Database(entities= [Movie::class,Genre::class],version = 1, )
@TypeConverters(StringConverter::class,GenreConverter::class,IntConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}