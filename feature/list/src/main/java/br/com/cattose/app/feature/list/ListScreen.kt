@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)

package br.com.cattose.app.feature.list

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
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
import coil.transform.RoundedCornersTransformation


@Composable
fun SharedTransitionScope.ListScreen(
    onItemClick: (CatImage) -> Unit,
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: ListViewModel = hiltViewModel()
) {
    val lazyPagingItems = viewModel.catsPagingData.collectAsLazyPagingItems()

    ListScreenContent(
        lazyPagingItems = lazyPagingItems,
        onItemClick = onItemClick,
        modifier = modifier,
        animatedVisibilityScope = animatedVisibilityScope
    )
}


@Composable
fun SharedTransitionScope.ListScreenContent(
    lazyPagingItems: LazyPagingItems<CatImage>,
    modifier: Modifier = Modifier,
    onItemClick: (CatImage) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val refreshState = rememberPullToRefreshState()

    if (refreshState.isRefreshing) {
        LaunchedEffect(Unit) {
            lazyPagingItems.refresh()
        }
    }

    when (lazyPagingItems.loadState.refresh) {
        LoadState.Loading -> {
            if (lazyPagingItems.itemSnapshotList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(ListTestTags.LOADING),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        is LoadState.Error -> {
            if (lazyPagingItems.itemSnapshotList.isEmpty()) {
                TryAgain(
                    message = stringResource(id = R.string.error_loading_message),
                    tryAgainActionText = stringResource(id = br.com.cattose.app.core.ui.R.string.action_retry),
                    onTryAgainClick = {
                        lazyPagingItems.refresh()
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            refreshState.endRefresh()
        }

        is LoadState.NotLoading -> {
            if (lazyPagingItems.itemSnapshotList.isEmpty() &&
                lazyPagingItems.loadState.append.endOfPaginationReached
            ) {
                TryAgain(
                    message = stringResource(id = R.string.empty_state_message),
                    tryAgainActionText = stringResource(id = br.com.cattose.app.core.ui.R.string.action_retry),
                    onTryAgainClick = {
                        lazyPagingItems.refresh()
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            refreshState.endRefresh()
        }
    }

    val columns =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
            2
        } else {
            3
        }

    Box(Modifier.nestedScroll(refreshState.nestedScrollConnection)) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(columns),
            modifier = modifier
                .padding(8.dp)
                .testTag(ListTestTags.LAZY_GRID)
        ) {
            items(lazyPagingItems.itemCount) {
                lazyPagingItems[it]?.let {
                    Box(
                        modifier = Modifier.padding(4.dp)
                    ) {
                        CatListItem(
                            cat = it,
                            animatedVisibilityScope = animatedVisibilityScope,
                            onItemClick = { cat ->
                                onItemClick(cat)
                            }
                        )
                    }
                }
            }

            when (lazyPagingItems.loadState.append) {
                is LoadState.Error -> {
                    item {
                        LaunchedEffect(Unit) {
                            lazyPagingItems.retry()
                        }
                    }
                }

                LoadState.Loading -> {
                    if (lazyPagingItems.itemSnapshotList.isNotEmpty()) {
                        item(span = StaggeredGridItemSpan.FullLine) {
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

                is LoadState.NotLoading -> Unit
            }
        }
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = refreshState,
        )
    }
}

@Composable
fun SharedTransitionScope.CatListItem(
    cat: CatImage,
    onItemClick: (CatImage) -> Unit,
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    DefaultAsyncImage(
        imageUrl = cat.imageUrl,
        modifier = modifier
            .clickable { onItemClick(cat) }
            .wrapContentSize()
            .sharedBounds(
                sharedContentState = rememberSharedContentState(key = cat.imageUrl),
                animatedVisibilityScope = animatedVisibilityScope,
                placeHolderSize = SharedTransitionScope.PlaceHolderSize.contentSize
            )
            .clip(RoundedCornerShape(8.dp))
            .testTag(cat.id),
        contentScale = ContentScale.Inside,
        errorPlaceholder = {
            ImagePlaceholder(
                drawable = br.com.cattose.app.core.ui.R.drawable.cat_placeholder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                transformations = listOf(RoundedCornersTransformation(16.dp.value))
            )
        },
        loadingPlaceholder = {
            ImagePlaceholder(
                drawable = br.com.cattose.app.core.ui.R.drawable.cat_placeholder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                transformations = listOf(RoundedCornersTransformation(16.dp.value))
            )
        }
    )
}
