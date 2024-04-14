package br.com.cattose.app.feature.list

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.cattose.app.core.ui.error.TryAgain
import br.com.cattose.app.core.ui.image.DefaultAsyncImage
import br.com.cattose.app.core.ui.image.ImagePlaceholder
import br.com.cattose.app.data.model.domain.CatImage


@Composable
fun LoginScreen(
    onItemClick: (CatImage) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel()
) {
    val lazyPagingItems = viewModel.catsPagingData.collectAsLazyPagingItems()

    ListScreenContent(
        lazyPagingItems = lazyPagingItems,
        onItemClick = onItemClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreenContent(
    lazyPagingItems: LazyPagingItems<CatImage>,
    modifier: Modifier = Modifier,
    onItemClick: (CatImage) -> Unit
) {
    val columns = getColumnsByOrientation(LocalConfiguration.current.orientation)

    val refreshState = rememberPullToRefreshState()

    if (refreshState.isRefreshing) {
        lazyPagingItems.refresh()
    }

    when (lazyPagingItems.loadState.refresh) {
        LoadState.Loading -> {
            if (lazyPagingItems.itemCount == 0) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("loading"),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        is LoadState.Error -> {
            if (lazyPagingItems.itemCount == 0) {
                TryAgain(
                    message = stringResource(id = R.string.error_loading_message),
                    tryAgainActionText = stringResource(id = br.com.cattose.app.core.ui.R.string.action_retry),
                    onTryAgainClick = {
                        lazyPagingItems.refresh()
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        is LoadState.NotLoading -> {
            if (lazyPagingItems.itemCount == 0) {
                TryAgain(
                    message = stringResource(id = R.string.empty_state_message),
                    tryAgainActionText = stringResource(id = br.com.cattose.app.core.ui.R.string.action_retry),
                    onTryAgainClick = {
                        lazyPagingItems.refresh()
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    Box(Modifier.nestedScroll(refreshState.nestedScrollConnection)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = modifier.padding(8.dp)
        ) {
            items(lazyPagingItems.itemCount) {
                lazyPagingItems[it]?.let {
                    Box(
                        modifier = Modifier.padding(4.dp)
                    ) {
                        CatListItem(it, { cat ->
                            onItemClick(cat)
                        })
                    }
                }
            }

            when (lazyPagingItems.loadState.append) {
                is LoadState.Error -> {
                    item {
                        LaunchedEffect(true) {
                            lazyPagingItems.retry()
                        }
                    }
                }

                LoadState.Loading -> {
                    if (lazyPagingItems.itemCount > 0) {
                        item(span = { GridItemSpan(columns) }) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }

                is LoadState.NotLoading -> {
                    refreshState.endRefresh()
                }
            }
        }
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = refreshState,
        )
    }
}

@Composable
fun CatListItem(
    cat: CatImage,
    onItemClick: (CatImage) -> Unit,
    modifier: Modifier = Modifier
) {
    DefaultAsyncImage(
        imageUrl = cat.imageUrl,
        modifier = modifier
            .clickable { onItemClick(cat) }
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .testTag(cat.id),
        contentScale = ContentScale.Crop,
        errorPlaceholder = {
            ImagePlaceholder(
                drawable = br.com.cattose.app.core.ui.R.drawable.cat_placeholder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        },
        loadingPlaceholder = {
            ImagePlaceholder(
                drawable = br.com.cattose.app.core.ui.R.drawable.cat_placeholder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    )
}

private fun getColumnsByOrientation(orientation: Int): Int {
    return if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        2
    } else {
        3
    }
}