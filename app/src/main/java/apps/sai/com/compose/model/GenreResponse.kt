package apps.sai.com.movieapp.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    @SerialName("genres") var genres: List<Genre> = arrayListOf(),
)

