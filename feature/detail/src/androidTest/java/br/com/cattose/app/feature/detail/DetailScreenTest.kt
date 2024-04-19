package br.com.cattose.app.feature.detail

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import br.com.cattose.app.data.model.domain.Breed
import br.com.cattose.app.data.model.domain.CatDetails
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun detailScreen_SuccessState() {
        val catDetails = CatDetails(
            id = "id",
            imageUrl = "https://cat1.jpeg",
            mainBreed = Breed(
                name = "Breed",
                description = "description",
                temperaments = listOf("Sweet", "Agile", "Friendly", "Calm")
            )
        )

        with(composeTestRule) {
            setContent {
                DetailsScreenContent(
                    state = DetailState.Success(catDetails),
                    onBackClick = {}) {
                }
            }
            onNodeWithTag(DetailTestTags.IMAGE).assertExists()
            onNodeWithTag(DetailTestTags.BREED_DETAILS).assertIsDisplayed()
            onNodeWithText("Breed").assertIsDisplayed()
            onNodeWithText("description").assertIsDisplayed()

            catDetails.mainBreed?.temperaments?.forEach {
                onNodeWithText(it).assertIsDisplayed()
            }
        }
    }

    @Test
    fun detailScreren_LoadingState() {
        composeTestRule.setContent {
            DetailsScreenContent(
                state = DetailState.Loading,
                onBackClick = {}) {
            }
        }
        composeTestRule.onNodeWithTag(DetailTestTags.LOADING).assertIsDisplayed()
    }

    @Test
    fun detailScreren_ErrorState() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val expectedString = context.getString(R.string.error_detail_loading)

        composeTestRule.setContent {
            DetailsScreenContent(
                state = DetailState.Error,
                onBackClick = {}) {
            }
        }
        composeTestRule.onNodeWithText(expectedString).assertIsDisplayed()
    }
}