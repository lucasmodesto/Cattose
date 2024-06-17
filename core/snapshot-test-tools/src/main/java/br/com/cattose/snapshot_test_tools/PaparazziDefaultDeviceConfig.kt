package br.com.cattose.snapshot_test_tools

import app.cash.paparazzi.DeviceConfig
import com.android.resources.ScreenOrientation

enum class PaparazziDefaultDeviceConfig(
    val deviceConfig: DeviceConfig
) {
    PIXEL_6(deviceConfig = DeviceConfig.PIXEL_6),
    PIXEL_6_LANDSCAPE(deviceConfig = DeviceConfig.PIXEL_6.copy(orientation = ScreenOrientation.LANDSCAPE))
}

enum class PaparazziTheme(
    val themeName: String
) {
    MATERIAL_LIGHT_NO_ACTION_BAR(themeName = "android:Theme.Material.Light.NoActionBar")
}
