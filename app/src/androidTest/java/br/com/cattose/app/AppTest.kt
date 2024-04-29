package br.com.cattose.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.espresso.device.DeviceInteraction.Companion.setScreenOrientation
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.espresso.device.action.ScreenOrientation
import androidx.test.espresso.device.rules.ScreenOrientationRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import br.com.cattose.MainActivity
import br.com.cattose.app.feature.detail.DetailTestTags
import br.com.cattose.app.feature.list.ListTestTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AppTest {

    @get:Rule
    val screenOrientationRule: ScreenOrientationRule =
        ScreenOrientationRule(ScreenOrientation.PORTRAIT)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun list_To_Detail_Navigation() {
        with(composeTestRule) {
            waitUntil {
                onNodeWithTag(ListTestTags.LAZY_GRID).isDisplayed()
            }

            onNodeWithTag(ListTestTags.LAZY_GRID)
                .onChildren()
                .onFirst()
                .performClick()

            waitUntil {
                onNodeWithTag(DetailTestTags.BREED_DETAILS).isDisplayed()
            }
            onNodeWithTag(DetailTestTags.IMAGE).assertIsDisplayed()
            onNodeWithTag(DetailTestTags.BACK_BUTTON).assertIsDisplayed()

            onNodeWithTag(DetailTestTags.BACK_BUTTON).performClick()

            waitForIdle()

            onNodeWithTag(ListTestTags.LAZY_GRID).assertExists().assertIsDisplayed()
            onNodeWithTag(DetailTestTags.IMAGE).assertDoesNotExist()
        }
    }

    @Test
    fun orientation_Change_Should_Keep_Grid_Items() {
        with(composeTestRule) {
            waitUntil {
                onNodeWithTag(ListTestTags.LAZY_GRID).isDisplayed()
            }

            onNodeWithTag(ListTestTags.LAZY_GRID)
                .onChildren()
                .onFirst()
                .assertExists()
                .assertIsDisplayed()

            onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)

            onNodeWithTag(ListTestTags.LOADING).assertIsNotDisplayed()

            onNodeWithTag(ListTestTags.LAZY_GRID)
                .onChildren()
                .onFirst()
                .assertExists()
                .assertIsDisplayed()
        }
    }

    @Test
    fun orientation_Change_Should_Keep_Detail_Screen_Content() {
        with(composeTestRule) {
            waitUntil {
                onNodeWithTag(ListTestTags.LAZY_GRID).isDisplayed()
            }

            onNodeWithTag(ListTestTags.LAZY_GRID)
                .onChildren()
                .onFirst()
                .performClick()

            waitForIdle()

            waitUntil {
                onNodeWithTag(DetailTestTags.BREED_DETAILS).isDisplayed()
            }

            onNodeWithTag(DetailTestTags.IMAGE).assertIsDisplayed()
            onNodeWithTag(DetailTestTags.BACK_BUTTON).assertIsDisplayed()

            onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)

            onNodeWithTag(DetailTestTags.LOADING).assertIsNotDisplayed()

            onNodeWithTag(DetailTestTags.IMAGE).assertIsDisplayed()
            onNodeWithTag(DetailTestTags.BACK_BUTTON).assertIsDisplayed()
        }
    }
}
