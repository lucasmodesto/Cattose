@file:OptIn(ExperimentalSharedTransitionApi::class)

package br.com.cattose.app.feature.list

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.cattose.app.core.ui.preview.SharedTransitionPreviewTheme
import br.com.cattose.app.data.model.domain.CatImage
import br.com.cattose.app.feature.list.ListTestTags.APPEND_LOADING
import br.com.cattose.app.feature.list.ListTestTags.EMPTY_LIST
import br.com.cattose.app.feature.list.ListTestTags.ERROR
import br.com.cattose.app.feature.list.ListTestTags.INITIAL_LOADING
import br.com.cattose.app.feature.list.ListTestTags.LAZY_GRID
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class ListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val cats = listOf(
        CatImage("1", "https://catimage1.jpg"),
        CatImage("2", "https://catimage2.jpg"),
        CatImage("3", "https://catimage3.jpg")
    )

    @Test
    fun successState() {
        val pagingDataFlow = flowOf(
            PagingData.from(
                data = cats, sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            )
        )

        composeTestRule.setContent {
            val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()
            SharedTransitionPreviewTheme {
                ListScreenContent(
                    lazyPagingItems = lazyPagingItems,
                    onItemClick = {},
                    animatedVisibilityScope = it
                )
            }
        }

        cats.forEach {
            composeTestRule.onNodeWithTag(it.id).assertIsDisplayed()
        }

        with(composeTestRule) {
            onNodeWithTag(LAZY_GRID).onChildren().assertCountEquals(3)
            onNodeWithTag(INITIAL_LOADING).assertIsNotDisplayed()
            onNodeWithTag(APPEND_LOADING).assertIsNotDisplayed()
            onNodeWithTag(ERROR).assertIsNotDisplayed()
            onNodeWithTag(EMPTY_LIST).assertIsNotDisplayed()
        }
    }

    @Test
    fun initialLoading() {
        val pagingDataFlow = flowOf(
            PagingData.from(
                data = listOf<CatImage>(), sourceLoadStates = LoadStates(
                    refresh = LoadState.Loading,
                    prepend = LoadState.Loading,
                    append = LoadState.Loading
                )
            )
        )

        composeTestRule.setContent {
            val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()
            SharedTransitionPreviewTheme {
                ListScreenContent(
                    lazyPagingItems = lazyPagingItems,
                    onItemClick = {},
                    animatedVisibilityScope = it
                )
            }
        }
        composeTestRule.onNodeWithTag(INITIAL_LOADING).assertIsDisplayed()
        composeTestRule.onNodeWithTag(APPEND_LOADING).assertIsNotDisplayed()
    }

    @Test
    fun appendLoading() {
        val pagingDataFlow = flowOf(
            PagingData.from(
                data = cats, sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.Loading
                )
            )
        )

        composeTestRule.setContent {
            val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()
            SharedTransitionPreviewTheme {
                ListScreenContent(
                    lazyPagingItems = lazyPagingItems,
                    onItemClick = {},
                    animatedVisibilityScope = it
                )
            }
        }
        with(composeTestRule) {
            onNodeWithTag(APPEND_LOADING).assertIsDisplayed()
            onNodeWithTag(INITIAL_LOADING).assertIsNotDisplayed()
            onNodeWithTag(ERROR).assertIsNotDisplayed()
            onNodeWithTag(EMPTY_LIST).assertIsNotDisplayed()
        }
    }

    @Test
    fun errorState() {
        val pagingDataFlow = flowOf(
            PagingData.from(
                data = listOf<CatImage>(), sourceLoadStates = LoadStates(
                    refresh = LoadState.Error(Exception()),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            )
        )

        composeTestRule.setContent {
            val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()
            SharedTransitionPreviewTheme {
                ListScreenContent(
                    lazyPagingItems = lazyPagingItems,
                    onItemClick = {},
                    animatedVisibilityScope = it
                )
            }
        }
        with(composeTestRule) {
            onNodeWithTag(ERROR).assertIsDisplayed()
            onNodeWithTag(APPEND_LOADING).assertIsNotDisplayed()
            onNodeWithTag(INITIAL_LOADING).assertIsNotDisplayed()
            onNodeWithTag(EMPTY_LIST).assertIsNotDisplayed()
            onNodeWithTag(LAZY_GRID).onChildren().assertCountEquals(0)
        }
    }

    @Test
    fun emptyState() {
        val pagingDataFlow = flowOf(
            PagingData.from(
                data = listOf<CatImage>(), sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(true),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            )
        )

        composeTestRule.setContent {
            val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()
            SharedTransitionPreviewTheme {
                ListScreenContent(
                    lazyPagingItems = lazyPagingItems,
                    onItemClick = {},
                    animatedVisibilityScope = it
                )
            }
        }
        with(composeTestRule) {
            onNodeWithTag(EMPTY_LIST).assertIsDisplayed()
            onNodeWithTag(ERROR).assertIsNotDisplayed()
            onNodeWithTag(APPEND_LOADING).assertIsNotDisplayed()
            onNodeWithTag(INITIAL_LOADING).assertIsNotDisplayed()
            onNodeWithTag(LAZY_GRID).onChildren().assertCountEquals(0)
        }
    }
}
