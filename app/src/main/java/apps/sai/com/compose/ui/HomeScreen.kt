package apps.sai.com.compose.ui

import MovieContentType
import MovieNavigationType
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirlineStops
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import apps.sai.com.compose.model.MovieType
import apps.sai.com.movieapp.compose.R
import apps.sai.com.movieapp.data.Movie
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder


@Composable
fun MovieHomeScreen(
    navigationType: MovieNavigationType,
    contentType: MovieContentType,
    onTabPressed: (MovieType) -> Unit,
    onCardPressed: (Movie) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    movieUiState: MovieUiState.Success,
    modifier: Modifier = Modifier
) {
    val navigationItemContentList = listOf(
        NavigationItemContent(
            movieType = MovieType.NOW_PLAYING,
            icon = Icons.Default.PlayArrow,
            text = stringResource(id = R.string.tab_now_playing)
        ),
        NavigationItemContent(
            movieType = MovieType.POPULAR,
            icon = Icons.Default.Groups,
            text = stringResource(id = R.string.tab_poular)
        ),
        NavigationItemContent(
            movieType = MovieType.TOP_RATED,
            icon = Icons.Default.AirlineStops,
            text = stringResource(id = R.string.tab_top_rated)
        ),
        NavigationItemContent(
            movieType = MovieType.UPCOMING,
            icon = Icons.Default.Update,
            text = stringResource(id = R.string.tab_upcoming)
        )
    )
    if (navigationType == MovieNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        val navigationDrawerContentDescription = stringResource(R.string.navigation_drawer)
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(
                    modifier = Modifier.width(dimensionResource(R.dimen.drawer_width)),
                    drawerContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                ) {
                    NavigationDrawerContent(
                        selectedDestination = movieUiState.currentMovieType,
                        onTabPressed = onTabPressed,
                        navigationItemContentList = navigationItemContentList,
                        modifier = Modifier
                            .wrapContentWidth()
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                            .padding(dimensionResource(R.dimen.drawer_padding_content))
                    )
                }
            },
            modifier = Modifier.testTag(navigationDrawerContentDescription)
        ) {
            MovieAppContent(
                navigationType = navigationType,
                contentType = contentType,
                onTabPressed = onTabPressed,
                onCardPressed = onCardPressed,
                movieUiState = movieUiState,
                navigationItemContentList = navigationItemContentList,
                modifier = modifier,
            )
        }
    } else {
        if (movieUiState.isShowingHomepage) {
            MovieAppContent(
                navigationType = navigationType,
                contentType = contentType,
                movieUiState = movieUiState,
                onTabPressed = onTabPressed,
                onCardPressed = onCardPressed,
                navigationItemContentList = navigationItemContentList,
                modifier = modifier,
            )
        } else {
            MovieAppContent(
                navigationType = navigationType,
                contentType = contentType,
                movieUiState = movieUiState,
                onTabPressed = onTabPressed,
                onCardPressed = onCardPressed,
                navigationItemContentList = navigationItemContentList,
                modifier = modifier,
            )
        }
    }

}

@Composable
private fun MovieAppContent(
    navigationType: MovieNavigationType,
    contentType: MovieContentType,
    movieUiState: MovieUiState.Success,
    onTabPressed: ((MovieType) -> Unit),
    onCardPressed: (Movie) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier)
    {
        Row(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(visible = navigationType == MovieNavigationType.NAVIGATION_RAIL) {
                val navigationRailContentDescription = stringResource(R.string.navigation_rail)
                MovieNavigationRail(
                    currentTab = movieUiState.currentMovieType,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier.testTag(navigationRailContentDescription)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                if (contentType == MovieContentType.LIST_AND_DETAIL) {

                    ListOnlyContent(
                        uiState = movieUiState,
                        onCardPressed = onCardPressed,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = dimensionResource(R.dimen.list_only_horizontal_padding))
                    )

                } else {
                    ListOnlyContent(
                        uiState = movieUiState,
                        onCardPressed = onCardPressed,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = dimensionResource(R.dimen.list_only_horizontal_padding))
                    )
                }
                AnimatedVisibility(
                    visible = navigationType == MovieNavigationType.BOTTOM_NAVIGATION
                ) {
                    val bottomNavigationContentDescription =
                        stringResource(R.string.navigation_bottom)
                    BottomNavigationBar(
                        currentTab = movieUiState.currentMovieType,
                        onTabPressed = onTabPressed,
                        navigationItemContentList = navigationItemContentList,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(bottomNavigationContentDescription)
                    )
                }
            }
        }
    }
}

@Composable
fun ListOnlyContent(
    uiState: MovieUiState.Success,
    onCardPressed: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {

    val movies = uiState.currentSelectedMovies
    val lazyNowPlayingPagingItems = movies.collectAsLazyPagingItems()


    LazyVerticalGrid(
        columns = GridCells.Fixed(1), contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.list_item_vertical_spacing)
        ), modifier = modifier
    ) {
        item {
            HomeTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.topbar_padding_vertical))
            )
        }
        items(lazyNowPlayingPagingItems.itemCount) { index ->
            lazyNowPlayingPagingItems[index]?.let { movie ->
                MovieListItem(movie, onClick = {
                    onCardPressed(movie)
                })
            }
        }
        lazyNowPlayingPagingItems.apply {
            when (loadState.append) {
                is LoadState.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        LoadingRow(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.grid_spacing)))
                    }
                }

                is LoadState.Error -> {
                    val message =
                        (loadState.append as? LoadState.Error)?.error?.message ?: return@apply

                    item(span = { GridItemSpan(maxLineSpan) }) {
                        ErrorScreen(
                            message = message,
                            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.grid_spacing)),
                            refresh = { retry() })
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun HomeTopBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        MovieAppLogo(
            modifier = Modifier
                .size(dimensionResource(R.dimen.topbar_logo_size))
                .padding(start = dimensionResource(R.dimen.topbar_logo_padding_start))
        )
    }
}

@Composable
private fun BottomNavigationBar(
    currentTab: MovieType,
    onTabPressed: ((MovieType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentTab == navItem.movieType,
                onClick = { onTabPressed(navItem.movieType) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                }
            )
        }
    }
}

@Composable
private fun NavigationDrawerContent(
    selectedDestination: MovieType,
    onTabPressed: ((MovieType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        NavigationDrawerHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.movie_image_padding)),
        )
        for (navItem in navigationItemContentList) {
            NavigationDrawerItem(
                selected = selectedDestination == navItem.movieType,
                label = {
                    Text(
                        text = navItem.text,
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.drawer_padding_header))
                    )
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                ),
                onClick = { onTabPressed(navItem.movieType) }
            )
        }
    }
}

@Composable
private fun NavigationDrawerHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MovieAppLogo(modifier = Modifier.size(dimensionResource(R.dimen.logo_size)))
        /* ReplyProfileImage(
             drawableResource = LocalAccountsDataProvider.defaultAccount.avatar,
             description = stringResource(id = R.string.profile),
             modifier = Modifier.size(dimensionResource(R.dimen.profile_image_size))
         )*/
    }
}

@Composable
fun MovieAppLogo(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Image(
        painter = painterResource(R.drawable.ic_launcher_foreground),
        contentDescription = stringResource(R.string.logo),
        colorFilter = ColorFilter.tint(color),
        modifier = modifier
    )
}

private data class NavigationItemContent(
    val movieType: MovieType,
    val icon: ImageVector,
    val text: String
)

@Composable
private fun MovieNavigationRail(
    currentTab: MovieType,
    onTabPressed: ((MovieType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationRailItem(
                selected = currentTab == navItem.movieType,
                onClick = { onTabPressed(navItem.movieType) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                }
            )
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