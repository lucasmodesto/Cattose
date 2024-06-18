@file:OptIn(ExperimentalSharedTransitionApi::class)

package br.com.cattose.app.feature.list

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.cattose.app.core.ui.preview.SharedTransitionPreviewTheme
import br.com.cattose.app.data.model.domain.CatImage
import kotlinx.coroutines.flow.flowOf

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