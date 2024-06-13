package br.com.cattose.snapshot_test_tools

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import app.cash.paparazzi.Paparazzi
import coil.Coil
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.test.FakeImageLoaderEngine
import com.android.resources.NightMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule

abstract class PaparazziTest(
    config: PaparazziDefaultDeviceConfig,
    nightMode: NightMode
) {

    @get:Rule
    val paparazzi: Paparazzi = Paparazzi(
        deviceConfig = config.deviceConfig.copy(nightMode = nightMode),
        theme = PaparazziTheme.MATERIAL_LIGHT_NO_ACTION_BAR.themeName
    )

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoilApi::class)
    @Before
    fun setupImageLoader() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val engine = FakeImageLoaderEngine.Builder()
            .intercept(
                { it is String && it.endsWith(".jpg").or(it.endsWith(".png")) },
                ColorDrawable(Color.MAGENTA)
            )
            .default(ColorDrawable(Color.BLUE))
            .build()
        val imageLoader = ImageLoader.Builder(paparazzi.context)
            .components { add(engine) }
            .build()
        Coil.setImageLoader(imageLoader)
    }
}
