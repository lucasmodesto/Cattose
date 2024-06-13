package br.com.cattose.snapshots.feature.detail

import br.com.cattose.app.feature.detail.DetailScreenErrorPreview
import br.com.cattose.app.feature.detail.DetailScreenLoadingPreview
import br.com.cattose.app.feature.detail.DetailScreenSuccessPreview
import br.com.cattose.snapshot_test_tools.PaparazziDefaultDeviceConfig
import br.com.cattose.snapshot_test_tools.PaparazziTest
import com.android.resources.NightMode
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class DetailScreenSnapshotTest(
    @TestParameter config: PaparazziDefaultDeviceConfig,
    @TestParameter nightMode: NightMode
): PaparazziTest(config, nightMode) {

    @Test
    fun successState() {
        paparazzi.snapshot {
            DetailScreenSuccessPreview()
        }
    }

    @Test
    fun errorState() {
        paparazzi.snapshot {
            DetailScreenErrorPreview()
        }
    }

    @Test
    fun loadingState() {
        paparazzi.snapshot {
            DetailScreenLoadingPreview()
        }
    }
}
