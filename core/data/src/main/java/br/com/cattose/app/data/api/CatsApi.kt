/*
 * Designed and developed by 2024 lucasmodesto (Lucas Modesto)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.cattose.app.data.api

import br.com.cattose.app.data.model.response.CatDetailResponse
import br.com.cattose.app.data.model.response.CatImageResponse

interface CatsApi {
    suspend fun fetchList(page: Int): List<CatImageResponse>
    suspend fun fetchDetails(id: String): CatDetailResponse
}