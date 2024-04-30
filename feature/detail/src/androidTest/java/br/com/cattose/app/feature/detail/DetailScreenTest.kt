package br.com.cattose.app.feature.detail

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import br.com.cattose.app.data.model.domain.Breed
import br.com.cattose.app.data.model.domain.CatDetails
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalSharedTransitionApi::class)
class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun successState() {
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
                SharedTransitionLayout {
                    AnimatedVisibility(visible = true, label = "") {
                        DetailsScreenContent(
                            state = DetailState(
                                catDetails = catDetails,
                                isLoading = false
                            ),
                            onBackClick = {},
                            onTryAgainClick = {},
                            animatedVisibilityScope = this
                        )
                    }
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
    fun loadingState() {
        composeTestRule.setContent {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true, label = "") {
                    DetailsScreenContent(
                        state = DetailState(
                            isLoading = true
                        ),
                        animatedVisibilityScope = this,
                        onBackClick = {},
                        onTryAgainClick = {})
                }
            }
        }
        composeTestRule.onNodeWithTag(DetailTestTags.LOADING).assertIsDisplayed()
    }

    @Test
    fun errorState() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val expectedString = context.getString(R.string.error_detail_loading)

        composeTestRule.setContent {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true, label = "") {
                    DetailsScreenContent(
                        state = DetailState(
                            isLoading = false,
                            hasError = true
                        ),
                        animatedVisibilityScope = this,
                        onBackClick = {},
                        onTryAgainClick = {})
                }

            }
        }
        composeTestRule.onNodeWithText(expectedString).assertIsDisplayed()
    }
}
