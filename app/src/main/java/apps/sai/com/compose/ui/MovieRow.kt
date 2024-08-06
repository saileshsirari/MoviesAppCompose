package apps.sai.com.compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import apps.sai.com.movieapp.compose.R
import apps.sai.com.movieapp.data.Genre
import apps.sai.com.movieapp.data.Movie
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
const val BASE_IMAGE_PREFIX ="https://image.tmdb.org/t/p/original/"
@Composable
fun TextRow(
    label: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = label, modifier = modifier, maxLines = 1, overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Normal, style = MaterialTheme.typography.titleSmall
    )
}

@Composable
fun TextRowBold(
    label: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = label, modifier = modifier, maxLines = 1, overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall
    )
}

@Composable
fun TextGenres(movie: Movie, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier, horizontalArrangement = Arrangement.Start) {
        movie.genres?.let {

           it.forEachIndexed { index, genre ->
                Text(
                    modifier = modifier,
                    text = genre?.name.orEmpty(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.titleSmall
                )
                if (index != it.size - 1 && it.size > 1) {
                    Text(text = ",", modifier = modifier.padding(end = 2.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MoviePoster(movie: Movie, modifier: Modifier) {
    GlideImage(
        modifier = modifier,
        loading = placeholder(R.drawable.ic_launcher_background),
        model = BASE_IMAGE_PREFIX + movie.posterPath.orEmpty(),
        contentDescription = " ${movie.originalTitle} image",
    )
}

@Composable
fun MovieListItem(movie: Movie, modifier: Modifier = Modifier, onClick: () -> Unit = {} ) {
    Card(
        onClick = onClick,
        modifier = modifier.padding(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            MoviePoster(movie,
                modifier = modifier
                    .widthIn(min = 120.dp)
                    .heightIn(max = 200.dp)
                    .padding(end = 10.dp)
            )
            MovieDescription(movie, modifier = modifier)
        }
    }
}

@Composable
fun MovieDescription(movie: Movie, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.Start) {
        TextRow(label = movie.title.orEmpty(), modifier)
        TextGenres(movie = movie, modifier)
        TextRow(label = movie.releaseDate.orEmpty(), modifier)
        if (movie.voteAverage != null && movie.voteAverage >= 8) {
            TextRowBold(label = movie.voteAverage.toString(), modifier)
        } else {
            TextRow(label = movie.voteAverage?.toString().orEmpty(), modifier)
        }
        TextRow(label = movie.spokenLanguagesFormatted.orEmpty(), modifier)
    }
}

@Preview
@Composable
fun MovieItemPreview() {
    val movie = Movie(
        1,
        genres = arrayListOf(
            Genre(id = 1, "Action"),
            Genre(id = 2, "Adventure"),
            Genre(id = 1, "Drama")
        ),
        title = "Movie Title",
        releaseDate = "2023-01-01",
        voteAverage = 8.5,

        )
    movie.spokenLanguagesFormatted = "English, German"
    MovieListItem(
        movie = movie,
        modifier = Modifier
            .widthIn(max = 700.dp)
            .heightIn(max = 300.dp),
    )
}