# Cattose
Jetpack compose sample app. The purpose is to use this code as a playground to test new android features.

## Main Development Libraries
- [Material3](https://m3.material.io/)
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [Ktor](https://ktor.io/)
- [Coil](https://coil-kt.github.io/coil/)
- [Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- [Espresso](https://developer.android.com/training/testing/espresso)
- [MocKK](https://mockk.io/)
- [Truth](https://truth.dev/)
- [Turbine](https://github.com/cashapp/turbine)

## Screenshots
|                               List screen                                |                               Detail screen                               |
|:------------------------------------------------------------------------:|:-------------------------------------------------------------------------:|
| <img src="/docs/images/cattose_list_dark_mode.png" width=50% height=50%> | <img src="/docs/images/cattose_detail_darkmode.png" width=50% height=50%> |

## Open API
Cattose uses [TheCatApi](https://thecatapi.com/) as the data source.
This API is a valuable resource for developers looking to incorporate cat images or information about various cat breeds into their applications.
It offers endpoints to access random cat images, search for specific cat breeds, and retrieve detailed information about each breed, 
including their characteristics and traits. For further details, visit the official [API documentation](https://developers.thecatapi.com/view-account/ylX4blBYT9FaoVd6OhvR?report=bOoHBz-8t).

There are some restrictions on the usage without providing the API KEY, which can be set in
the [secrets](/secrets.properties) file, however it is possible to test the application without the key.

## License
This project contains code from [nowinandroid] by [Android developers team], licensed under the Apache License, Version 2.0. For more information, see [https://github.com/android/nowinandroid].
Cattose is distributed under the terms of the Apache License (Version 2.0). See the [license](LICENSE) for more information.