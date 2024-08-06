package apps.sai.com.movieapp.db

import androidx.room.TypeConverter
import apps.sai.com.movieapp.data.Genre
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


abstract class BaseConverter<T>(type: Type) {
    private val gson: Gson = GsonBuilder()
        .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
        .create()

    private val type: Type = type

    @TypeConverter
    fun mapStringToList(value: String): List<T> {
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun mapListToString(value: List<T>): String {
        return gson.toJson(value, type)
    }
}

class StringConverter :
    BaseConverter<String>(object : TypeToken<List<String>>() {}.type)

class IntConverter :
    BaseConverter<Int>(object : TypeToken<List<Int>>() {}.type)

class GenreConverter :
    BaseConverter<Genre>(object : TypeToken<List<Genre>>() {}.type)
