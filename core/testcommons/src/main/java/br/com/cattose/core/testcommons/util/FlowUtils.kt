package br.com.cattose.core.testcommons.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <reified T> delayedFlowOf(
    vararg elements: T,
    delayMillis: Long = 100
): Flow<T> =
    flow {
        delay(delayMillis)
        for (element in elements) {
            emit(element)
        }
    }
