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

package br.com.cattose.app.data.mapper

import br.com.cattose.app.data.model.domain.Breed
import br.com.cattose.app.data.model.response.BreedResponse

fun BreedResponse.toDomain() = Breed(
    description = description.orEmpty(),
    name = name,
    temperaments = temperament?.split(",")?.map { it.trim() } ?: emptyList()
)
