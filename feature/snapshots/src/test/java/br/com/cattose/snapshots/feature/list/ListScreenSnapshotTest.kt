package br.com.cattose.snapshots.feature.list

import androidx.compose.ui.platform.ComposeView
import br.com.cattose.app.feature.list.ListScreenAppendLoadingPreview
import br.com.cattose.app.feature.list.ListScreenEmptyPreview
import br.com.cattose.app.feature.list.ListScreenErrorPreview
import br.com.cattose.app.feature.list.ListScreenRefreshLoadingPreview
import br.com.cattose.snapshot_test_tools.PaparazziConfig
import br.com.cattose.snapshot_test_tools.PaparazziTest
import com.android.resources.NightMode
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class ListScreenSnapshotTest(
    @TestParameter config: PaparazziConfig,
    @TestParameter nightMode: NightMode
): PaparazziTest(config, nightMode) {

    @Test
    fun errorState() {
        paparazzi.snapshot {
            ListScreenErrorPreview()
        }
    }

    @Test
    fun emptyState() {
        paparazzi.snapshot {
            ListScreenEmptyPreview()
        }
    }

    @Test
    fun refreshLoadingState() {
        val view = ComposeView(paparazzi.context)
        view.setContent {
            ListScreenRefreshLoadingPreview()
        }
        paparazzi.gif(view = view)
    }

    @Test
    fun appendLoadingState() {
        val view = ComposeView(paparazzi.context)
        view.setContent {
            ListScreenAppendLoadingPreview()
        }
        paparazzi.gif(view = view)
    }
}
