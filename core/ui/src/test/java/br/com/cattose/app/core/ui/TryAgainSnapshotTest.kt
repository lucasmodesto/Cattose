package br.com.cattose.app.core.ui

import br.com.cattose.app.core.ui.error.TryAgainPreview
import br.com.cattose.snapshot_test_tools.PaparazziDefaultDeviceConfig
import br.com.cattose.snapshot_test_tools.PaparazziTest
import com.android.resources.NightMode
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class TryAgainSnapshotTest(
    @TestParameter config: PaparazziDefaultDeviceConfig,
    @TestParameter nightMode: NightMode
): PaparazziTest(config, nightMode) {

    @Test
    fun tryAgain() {
        paparazzi.snapshot {
            TryAgainPreview()
        }
    }
}
