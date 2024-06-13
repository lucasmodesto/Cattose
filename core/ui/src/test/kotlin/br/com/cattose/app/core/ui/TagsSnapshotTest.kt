package br.com.cattose.app.core.ui

import app.cash.paparazzi.Paparazzi
import br.com.cattose.app.core.ui.tags.TagListPreview
import com.android.resources.NightMode
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class TagsSnapshotTest(
    @TestParameter config: PaparazziDefaultDeviceConfig,
    @TestParameter nightMode: NightMode
) {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = config.deviceConfig.copy(
            nightMode = nightMode
        ),
        theme = PaparazziTheme.MATERIAL_LIGHT_NO_ACTION_BAR.themeName
    )

    @Test
    fun tagListSnapshotTest() {
        paparazzi.snapshot {
            TagListPreview()
        }
    }
}
