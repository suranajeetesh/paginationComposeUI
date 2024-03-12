package com.example.composelist.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.composelist.core.uI.BaseActivity
import com.example.composelist.data.remote.model.response.userData.DataList
import com.example.composelist.ui.items.UserCard
import com.example.composelist.ui.items.UserDataLists
import com.example.composelist.ui.theme.ComposeListTheme
import com.example.composelist.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.toList


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
        setContent {
            ComposeListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    UserDataLists(this,homeViewModel) {
                        Log.e("TAG","onCreate() --> ${it?.first_name}")
                        Toast.makeText(this, "${it?.first_name}", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
//        callAPI()
    }

    private fun initObserver() {

    }

//    private fun callAPI() {
//        lifecycleScope.launch {
//            homeViewModel.userData.collectAsLazyPagingItems().collect { lazyPagingItems ->
//                val dataList: List<DataList> = lazyPagingItems.snapshot().items
//
//                // Now you can work with the list of items in dataList
//                // For example, update your UI, perform operations, etc.
//            }
//        }
//    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeListTheme {
        UserCard(DataList("", "test", "test", 0, "test")){

        }
    }
}
