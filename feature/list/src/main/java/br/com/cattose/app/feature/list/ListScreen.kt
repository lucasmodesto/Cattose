/*
 * Designed and developed by 2024 lucasmodesto (Lucas Modesto)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.cattose.app.core.ui.error.TryAgain
import br.com.cattose.app.core.ui.image.DefaultAsyncImage
import br.com.cattose.app.core.ui.image.ImagePlaceholder
import br.com.cattose.app.core.ui.preview.SharedTransitionPreviewTheme
import br.com.cattose.app.data.model.domain.CatImage
import br.com.cattose.app.feature.list.ListTestTags.APPEND_LOADING
import br.com.cattose.app.feature.list.ListTestTags.EMPTY_LIST
import br.com.cattose.app.feature.list.ListTestTags.ERROR
import br.com.cattose.app.feature.list.ListTestTags.INITIAL_LOADING
import br.com.cattose.app.feature.list.ListTestTags.LAZY_GRID
import coil.transform.RoundedCornersTransformation
import kotlinx.coroutines.flow.flowOf


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
                        .testTag(INITIAL_LOADING),
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
                    modifier = Modifier.fillMaxSize().testTag(ERROR)
                )
            }
            refreshState.endRefresh()
        }

        is LoadState.NotLoading -> {
            if (lazyPagingItems.itemSnapshotList.isEmpty()) {
                TryAgain(
                    message = stringResource(id = R.string.empty_state_message),
                    tryAgainActionText = stringResource(id = br.com.cattose.app.core.ui.R.string.action_retry),
                    onTryAgainClick = {
                        lazyPagingItems.refresh()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(EMPTY_LIST)
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
                .testTag(LAZY_GRID)
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
                                    .padding(16.dp)
                                    .testTag(APPEND_LOADING),
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
            .sharedElement(
                state = rememberSharedContentState(key = cat.imageUrl),
                animatedVisibilityScope = animatedVisibilityScope
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

@PreviewLightDark
@PreviewScreenSizes
@Composable
fun ListScreenSuccessPreview() {
    SharedTransitionPreviewTheme {
        val cats = mutableListOf<CatImage>()
        repeat(10) {
            cats.add(CatImage(it.toString(), "https://cdn2.thecatapi.com/images/zKO1twSOV.jpg"))
        }

        val pagingDataFlow = flowOf(
            PagingData.from(
                data = cats, sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            )
        )

        Surface {
            ListScreenContent(
                lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems(),
                onItemClick = {},
                animatedVisibilityScope = it
            )
        }
    }
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
fun ListScreenRefreshLoadingPreview() {
    SharedTransitionPreviewTheme {
        val pagingDataFlow = flowOf(
            PagingData.from(
                data = emptyList<CatImage>(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Loading,
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            )
        )

        Surface {
            ListScreenContent(
                lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems(),
                onItemClick = {},
                animatedVisibilityScope = it
            )
        }
    }
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
fun ListScreenAppendLoadingPreview() {
    val cats = mutableListOf<CatImage>()
    repeat(3) {
        cats.add(CatImage(it.toString(), "https://cdn2.thecatapi.com/images/zKO1twSOV.jpg"))
    }

    SharedTransitionPreviewTheme {
        val pagingDataFlow = flowOf(
            PagingData.from(
                data = cats,
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.Loading
                )
            )
        )

        Surface {
            ListScreenContent(
                lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems(),
                onItemClick = {},
                animatedVisibilityScope = it
            )
        }
    }
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
fun ListScreenErrorPreview() {
    SharedTransitionPreviewTheme {
        val pagingDataFlow = flowOf(
            PagingData.from(
                data = emptyList<CatImage>(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Error(Exception()),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            )
        )

        Surface {
            ListScreenContent(
                lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems(),
                onItemClick = {},
                animatedVisibilityScope = it
            )
        }
    }
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
fun ListScreenEmptyPreview() {
    SharedTransitionPreviewTheme {
        val pagingDataFlow = flowOf(
            PagingData.from(
                data = emptyList<CatImage>(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            )
        )

        Surface {
            ListScreenContent(
                lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems(),
                onItemClick = {},
                animatedVisibilityScope = it
            )
        }
    }
}
