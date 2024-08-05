package apps.sai.com.compose.model

import apps.sai.com.movieapp.data.Movie
import com.google.gson.annotations.Expose
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
data class MovieResponse(
    @SerialName("dates") var dates: Dates? = null,
    @SerialName("page") var page: Int? = null,
    @SerialName("results") var results: ArrayList<Movie> = arrayListOf(),
    @SerialName("total_pages") var totalPages: Int? = null,
    @SerialName("total_results") var totalResults: Int? = null
)

@Serializable
data class Dates(
    val maximum: String="",
    val minimum:String=""
)

