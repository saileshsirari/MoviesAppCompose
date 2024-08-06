package apps.sai.com.compose.ui

import MovieContentType
import MovieNavigationType
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MovieApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,

    ) {
    val viewModel: HomeViewModel = viewModel()
    val navigationType: MovieNavigationType
    val contentType: MovieContentType
    val movieUiState = viewModel.movieUiState.collectAsState()
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = MovieNavigationType.BOTTOM_NAVIGATION
            contentType = MovieContentType.LIST_ONLY
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = MovieNavigationType.NAVIGATION_RAIL
            contentType = MovieContentType.LIST_ONLY
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = MovieNavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = MovieContentType.LIST_AND_DETAIL
        }

        else -> {
            navigationType = MovieNavigationType.BOTTOM_NAVIGATION
            contentType = MovieContentType.LIST_ONLY
        }
    }
    if(movieUiState.value is MovieUiState.Success) {
        MovieHomeScreen(navigationType, contentType, {

        }, {

        }, {

        }, movieUiState.value as MovieUiState.Success, modifier)
    }else if(movieUiState.value is MovieUiState.Loading){
        LoadingScreen(modifier)
    }

}

@Composable
fun LoadingScreen(modifier: Modifier) {

}
