package br.com.cattose.snapshots.feature.list

import br.com.cattose.app.feature.list.ListScreenAppendLoadingPreview
import br.com.cattose.app.feature.list.ListScreenEmptyPreview
import br.com.cattose.app.feature.list.ListScreenErrorPreview
import br.com.cattose.app.feature.list.ListScreenRefreshLoadingPreview
import br.com.cattose.snapshot_test_tools.PaparazziDefaultDeviceConfig
import br.com.cattose.snapshot_test_tools.PaparazziTest
import com.android.resources.NightMode
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class ListScreenSnapshotTest(
    @TestParameter config: PaparazziDefaultDeviceConfig,
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
        paparazzi.snapshot {
            ListScreenRefreshLoadingPreview()
        }
    }

    @Test
    fun appendLoadingState() {
        paparazzi.snapshot {
            ListScreenAppendLoadingPreview()
        }
    }
}
