package com.example.financetracker_app.helper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/**
 Note: The typical flow combine operator only takes a max of 5 flows to transform
 but there are uses cases in the app that require more. So this is an extension function
 to handle such scenarios.
 */
fun <T1, T2, T3, T4, T5, T6, T7, T8, R> combine(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8) -> R
): Flow<R> = combine(
    combine(flow1, flow2, flow3, ::Triple),
    combine(flow4, flow5, flow6, ::Triple),
    combine(flow7, flow8, ::Pair)
) { t1, t2, t3 ->
    transform(
        t1.first,
        t1.second,
        t1.third,
        t2.first,
        t2.second,
        t2.third,
        t3.first,
        t3.second
    )
}
