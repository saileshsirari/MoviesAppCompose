package apps.sai.com.compose.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.compose.collectAsLazyPagingItems
import apps.sai.com.movieapp.compose.R
import apps.sai.com.movieapp.data.Movie
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun HomeScreen(modifier: Modifier, viewModel: HomeViewModel = viewModel()) {
    val lazyNowPlayingPagingItems = viewModel.nowPlaying.collectAsLazyPagingItems()
    LazyVerticalGrid(columns = GridCells.Fixed(1),modifier =modifier) {
        items(lazyNowPlayingPagingItems.itemCount) { index ->
            lazyNowPlayingPagingItems[index]?.let { movie ->
                MovieItem(movie)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MoviePoster(movie: Movie?, modifier: Modifier) {
    GlideImage(
        modifier = modifier,
        loading = placeholder(R.drawable.ic_launcher_background),
        model = BASE_IMAGE_PREFIX + movie?.posterPath.orEmpty(),
        contentDescription = " ${movie?.originalTitle} image",
    )
}