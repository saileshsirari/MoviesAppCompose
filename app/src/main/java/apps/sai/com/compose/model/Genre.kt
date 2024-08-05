package apps.sai.com.movieapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Genre(
    @PrimaryKey @SerialName("id") var id: Int ,
    @SerialName("name") var name: String
)


