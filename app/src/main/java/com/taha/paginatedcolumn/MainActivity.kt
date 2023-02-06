package com.taha.paginatedcolumn

import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gitub.taha.PaginatedColumn
import com.taha.paginatedcolumn.Data.data
import com.taha.paginatedcolumn.ui.theme.PaginatedColumnTheme
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaginatedColumnTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    showPaginatedList()
                }
            }
        }
    }
}

@Composable
fun showPaginatedList() {
    val isrefereshing = remember {
        mutableStateOf(false)
    }
    val coroutine = rememberCoroutineScope()
    PaginatedColumn(
        modifier =  Modifier.wrapContentSize(),
        item = {
            item(it)
        },
        loader = {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(80.dp),
                Alignment.Center
            ){
                CircularProgressIndicator(
                    strokeWidth = 1.dp,
                    color = Color.Red
                )
            }
        },
        pageSize = 10,
        totalPages = 2,
        paginationCall = {
            isrefereshing.value = true

            coroutine.launch {
                delay(5000)
                val items =  mutableListOf<String>().apply{
                    addAll(data)
                    addAll(data)
                }
                data.addAll(items)
                isrefereshing.value = false

            }



        },
        data = data,
        refereshing = isrefereshing.value,
    )
}

@OptIn(DelicateCoroutinesApi::class)
fun paginationCall(): Boolean {

    return true
}

@Composable
fun item(data: String) {
    Box(
        modifier = Modifier.height(56.dp)
    ) {
        Text(
            text = data,
            modifier = Modifier
                .background(Color.LightGray)
        )
    }
}


object Data {
    val data = mutableListOf<String>().apply {
        add("first")
        add("second")
        add("third")
        add("forth")
        add("fifth")
        add("sixth")
        add("seven")
        add("eight")
        add("nine")
        add("ten")
        add("eleven")
        add("twelve")
        add("thirteen")
        add("forteen")
        add("fifteen")
        add("sixteen")

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PaginatedColumnTheme {
    }
}