package com.gitub.taha


import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember


data class ScrollContext(
    val isTop: Boolean,
    val isBottom: Boolean,
)

val LazyListState.isLastItemVisible: Boolean
    get() {
        val visibleItemsInfo = layoutInfo.visibleItemsInfo
        return if (layoutInfo.totalItemsCount == 0) {
            false
        } else {
            val lastVisibleItem = visibleItemsInfo.last()
            val viewportHeight = layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset

            (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount &&
                    lastVisibleItem.offset + lastVisibleItem.size <= viewportHeight)
        }
    }
val LazyListState.lastItem: Int
    get() = layoutInfo.totalItemsCount - 1

val LazyListState.isFirstItemVisible: Boolean
    get() = firstVisibleItemIndex == 0

@Composable
fun rememberScrollContext(listState: LazyListState): ScrollContext {
    val scrollContext by remember {
        derivedStateOf {
            ScrollContext(
                isTop = listState.isFirstItemVisible,
                isBottom = listState.isLastItemVisible
            )
        }
    }
    return scrollContext
}