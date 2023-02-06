package com.gitub.taha

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "PaginatedColumn"

@Composable
fun <T> PaginatedColumn(
    modifier: Modifier = Modifier,
    item: @Composable (data: T) -> Unit,
    loader: @Composable () -> Unit ,
    paginationCall: () -> Unit,
    pageSize: Int = 10,
    totalPages: Long = 10,
    data: List<T>,
    refereshing:Boolean = false
) {
    var pageNumber = remember {
        mutableStateOf(1)
    }
    var stopReloading = remember{
        mutableStateOf(false)
    }


    val lazyListState = rememberLazyListState()

    Column(modifier = modifier) {
        LazyColumn(
            state = lazyListState,

            modifier = Modifier
                .fillMaxWidth()
        ) {
            itemsIndexed(items = data) { _, item ->
                item(item)
            }
            item{
                if (refereshing) {

                    Log.d(TAG, "PaginatedColumn: refersh item is added ")
                    loader()
                }
            }
        }
    }


    // manipulate the scroll context according to the list state
    val scrollContext = rememberScrollContext(lazyListState)

    // update the data when it has reached to the bottom of the page
    if (!stopReloading.value && scrollContext.isBottom) {

        //prevent duplicate event due to recompose too quickly
        if (!refereshing && (lazyListState.lastItem ) >= (pageNumber.value * pageSize)) {

            if ( checkNeededToLoadedNewData(pageNumber.value, totalPages)) {
                if (pageNumber.value >= 1) {
                    pageNumber.value++
                    paginationCall()
                }
            }else{
                stopReloading.value = true
            }
        }
    }
}



/**
 * checks whether the last item has loaded or not
 */
private fun checkNeededToLoadedNewData(pageNumber: Int, totalPages: Long): Boolean {
    return (pageNumber.toLong() != totalPages)
}




