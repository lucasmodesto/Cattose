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

package br.com.cattose.app.feature.detail

import android.content.Context
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import br.com.cattose.app.core.ui.preview.SharedTransitionPreviewTheme
import br.com.cattose.app.data.model.domain.Breed
import br.com.cattose.app.data.model.domain.CatDetails
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalSharedTransitionApi::class)
class DetailRouteScreenTest {

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
                SharedTransitionPreviewTheme {
                    DetailsScreenContent(
                        state = DetailState(
                            catDetails = catDetails,
                            isLoading = false
                        ),
                        onBackClick = {},
                        onTryAgainClick = {},
                        animatedVisibilityScope = it
                    )
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
            SharedTransitionPreviewTheme {
                DetailsScreenContent(
                    state = DetailState(
                        isLoading = true
                    ),
                    animatedVisibilityScope = it,
                    onBackClick = {},
                    onTryAgainClick = {})
            }
        }
        composeTestRule.onNodeWithTag(DetailTestTags.LOADING).assertIsDisplayed()
    }

    @Test
    fun errorState() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val expectedString = context.getString(R.string.error_detail_loading)

        composeTestRule.setContent {
            SharedTransitionPreviewTheme {
                DetailsScreenContent(
                    state = DetailState(
                        isLoading = false,
                        hasError = true
                    ),
                    animatedVisibilityScope = it,
                    onBackClick = {},
                    onTryAgainClick = {})
            }
        }
        composeTestRule.onNodeWithText(expectedString).assertIsDisplayed()
    }
}
