package br.com.cattose.app.feature.list

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import br.com.cattose.app.core.domain.model.CatImage
import org.junit.Rule
import org.junit.Test

class ListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun listScreen_SuccessState() {
        val cats = listOf(
            CatImage(id = "1", imageUrl = "https://cat1.jpg"),
            CatImage(id = "2", imageUrl = "https://cat2.jpg")
        )
        val state = ListState.Success(cats)

        composeTestRule.setContent {
            LoginScreenContent(
                state = state,
                onItemClick = {},
                onTryAgainClick = {}
            )
        }
        cats.forEach { cat ->
            composeTestRule.onNodeWithTag(cat.id).assertIsDisplayed()
        }
    }

    @Test
    fun listScreen_LoadingState() {
        composeTestRule.setContent {
            LoginScreenContent(
                state = ListState.Loading,
                onItemClick = {},
                onTryAgainClick = {})
        }
        composeTestRule.onNodeWithTag("loading").assertIsDisplayed()
    }

    @Test
    fun listScreen_EmptyState() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val expectedString = context.getString(R.string.empty_state_message)

        composeTestRule.setContent {
            LoginScreenContent(
                state = ListState.Empty,
                onItemClick = {},
                onTryAgainClick = {})
        }
        composeTestRule.onNodeWithText(expectedString).assertIsDisplayed()
    }

    @Test
    fun listScreen_ErrorState() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val expectedString = context.getString(R.string.error_loading_message)

        composeTestRule.setContent {
            LoginScreenContent(
                state = ListState.Error,
                onItemClick = {},
                onTryAgainClick = {})
        }
        composeTestRule.onNodeWithText(expectedString, ignoreCase = true).assertIsDisplayed()
    }
}