/*
 * Copyright 2023 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package br.com.cattose.app.data.api

import br.com.cattose.app.data.asset.AssetManager
import br.com.cattose.app.data.model.response.CatDetailResponse
import br.com.cattose.app.data.model.response.CatImageResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

private const val FILE_JSON_LIST = "list.json"
private const val FILE_JSON_DETAIL = "detail.json"

@OptIn(ExperimentalSerializationApi::class)
class DemoCatsApi @Inject constructor(
    private val json: Json,
    private val assetManager: AssetManager
) : CatsApi {

    override suspend fun fetchList(page: Int): List<CatImageResponse> {
        return assetManager.open(FILE_JSON_LIST).use(json::decodeFromStream)
    }

    override suspend fun fetchDetails(id: String): CatDetailResponse {
        return assetManager.open(FILE_JSON_DETAIL).use(json::decodeFromStream)
    }
}