@file:OptIn(ExperimentalSharedTransitionApi::class)

package br.com.cattose.app.feature.list

import android.content.Context
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.test.core.app.ApplicationProvider
import br.com.cattose.app.core.ui.preview.SharedTransitionPreviewTheme
import br.com.cattose.app.data.model.domain.CatImage
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class ListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun successState() {
        val cats = listOf(
            CatImage("1", "https://catimage1.jpg"),
            CatImage("2", "https://catimage2.jpg"),
            CatImage("3", "https://catimage3.jpg")
        )

        val pagingData = mockk<LazyPagingItems<CatImage>> {
            every { loadState.append } returns LoadState.NotLoading(endOfPaginationReached = false)
            every { loadState.refresh } returns LoadState.NotLoading(endOfPaginationReached = false)
            every { itemSnapshotList.items } returns cats
            every { itemSnapshotList.isEmpty() } returns false
            every { itemSnapshotList.isNotEmpty() } returns true
            every { itemCount } returns cats.size
            every { get(0) } returns cats[0]
            every { get(1) } returns cats[1]
            every { get(2) } returns cats[2]
        }

        composeTestRule.setContent {
            SharedTransitionPreviewTheme {
                ListScreenContent(
                    lazyPagingItems = pagingData,
                    onItemClick = {},
                    animatedVisibilityScope = it
                )
            }
        }

        cats.forEach {
            composeTestRule.onNodeWithTag(it.id).assertIsDisplayed()
        }

        composeTestRule.onNodeWithTag(ListTestTags.LAZY_GRID)
            .onChildren().assertCountEquals(3)
    }

    @Test
    fun loadingState() {
        val pagingData = mockk<LazyPagingItems<CatImage>>(relaxed = true) {
            every { itemSnapshotList.isEmpty() } returns false
            every { itemSnapshotList.isNotEmpty() } returns true
            every { loadState.refresh } returns LoadState.Loading
        }

        composeTestRule.setContent {
            SharedTransitionPreviewTheme {
                ListScreenContent(
                    lazyPagingItems = pagingData,
                    onItemClick = {},
                    animatedVisibilityScope = it
                )
            }
        }
        composeTestRule.onNodeWithTag(ListTestTags.LOADING).assertIsDisplayed()
    }

    @Test
    fun errorState() {
        val pagingData = mockk<LazyPagingItems<CatImage>>(relaxed = true) {
            every { itemSnapshotList.isEmpty() } returns false
            every { itemSnapshotList.isNotEmpty() } returns true
            every { loadState.refresh } returns LoadState.Error(Exception())
        }
        val context = ApplicationProvider.getApplicationContext<Context>()
        val expectedString = context.getString(R.string.error_loading_message)

        composeTestRule.setContent {
            SharedTransitionPreviewTheme {
                ListScreenContent(
                    lazyPagingItems = pagingData,
                    onItemClick = {},
                    animatedVisibilityScope = it
                )
            }
        }
        composeTestRule.onNodeWithText(expectedString).assertIsDisplayed()
    }
}
