package br.com.cattose.app.core.ui

import br.com.cattose.app.core.ui.tags.TagListPreview
import br.com.cattose.snapshot_test_tools.PaparazziConfig
import br.com.cattose.snapshot_test_tools.PaparazziTest
import com.android.resources.NightMode
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class TagsSnapshotTest(
    @TestParameter config: PaparazziConfig,
    @TestParameter nightMode: NightMode
) : PaparazziTest(config, nightMode) {

    @Test
    fun tagList() {
        paparazzi.snapshot {
            TagListPreview()
        }
    }
}
