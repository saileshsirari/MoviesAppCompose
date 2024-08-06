package apps.sai.com.movieapp.data

import android.content.Context
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import apps.sai.com.movieapp.compose.R
import apps.sai.com.movieapp.data.Utils.format2Places
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Entity
@Serializable
data class Movie(
    @PrimaryKey @SerialName("id") val id: Int,
    @SerialName("adult") val adult: Boolean? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("genres") var genres: List<Genre> = arrayListOf(),
    @SerialName("genre_ids") var genreIds: List<Int> = arrayListOf(),
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("overview") val overview: String? = null,
    @SerialName("popularity") val popularity: Double? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("video") val video: Boolean? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("vote_count") val voteCount: Int? = null
){
    var releaseDateFormatted: String? = null
    var spokenLanguagesFormatted:String? =null
    var genresFormatted:String? =null
    var voteAverageFormatted:String ? =null
    companion object {
        fun Movie.format(context: Context) {
            releaseDate?.let {
                releaseDateFormatted =
                    "${context.getString(R.string.release_date)} ${Utils.getFormatedDate(it)}"
            }

            originalLanguage?.let {
                spokenLanguagesFormatted =
                    "${context.getString(R.string.language)} ${Locale(it).displayName}"
            }
            voteCount?.let {
                if(it>0) {
                    voteAverageFormatted = "${context.getString(R.string.ratings)} " +
                            "${voteAverage?.format2Places()} ( $voteCount ${context.getString(R.string.votes)} )"
                }
            }

            genresFormatted =""
            if(!genres.isNullOrEmpty()) {
                genres?.forEach {
                    genresFormatted += it?.name + " "
                }
            }

            if (genreIds.isNullOrEmpty()) {
                genreIds = arrayListOf()
            }
        }
    }
}
object  Utils{
    const val YYYY_MM_DD_DASH = "yyyy-MM-dd"
    const val DD_MMM_YYYY = "dd/MMM/yyyy"
    fun getFormatedDate(text: String?): String {
        return (text?.toDate(Utils.YYYY_MM_DD_DASH)
            ?.formatDate(Utils.DD_MMM_YYYY).safe())
    }
    fun Double.format2Places():Double{
        return String.format("%.2f",this).toDouble()
    }
}

fun String?.safe(): String {
    if (this == null)
        return ""

    return this
}
fun String.toDate(
    pattern: String,
    locale: Locale = Locale.ENGLISH,
    timeZone: TimeZone = TimeZone.getDefault()
): Date? {
    return try {
        SimpleDateFormat(pattern, locale).apply { this.timeZone = timeZone }.parse(this)
    } catch (e: Exception) {
        null
    }
}
fun Any.formatDate(
    pattern: String,
    locale: Locale = Locale.ENGLISH,
    timeZone: TimeZone = TimeZone.getDefault()
): String? {
    return try {
        SimpleDateFormat(pattern, locale).apply { this.timeZone = timeZone }.format(this)
    } catch (e: Exception) {
        null
    }
}



