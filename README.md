<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=26"><img alt="API" src="https://img.shields.io/badge/API-26%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/lucasmodesto/Cattose/actions/workflows/android-ci.yaml/badge.svg?branch=main"><img alt="Build Status" src=https://github.com/lucasmodesto/Cattose/actions/workflows/android-ci.yaml/badge.svg?branch=main/></a> <br>
</p>

# Cattose
Cattose is a [Jetpack Compose](https://developer.android.com/develop/ui/compose) Sample App. This project serves as a sandbox environment to experiment and explore new features in Android development.

The app currently includes two screens: a list screen that shows cats in a grid, and a detail screen that provides information about the breed of each cat.

## Architecture
Cattose follows the MVVM pattern with the repository approach and integrates elements of Clean Architecture, such as using dependency injection with interfaces, resulting in a decoupled design that is flexible and adaptable to changes.
which is aligned to [Android Architecture Guide](https://developer.android.com/topic/architecture) from Google.

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
This project contains code and inspirations from [nowinandroid](https://github.com/android/nowinandroid) app, licensed under the Apache License, Version 2.0.
Cattose is distributed under the terms of the Apache License (Version 2.0). See the [license](LICENSE) for more information.
