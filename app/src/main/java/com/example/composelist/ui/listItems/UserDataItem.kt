package com.example.composelist.ui.listItems

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.composelist.R
import com.example.composelist.data.remote.model.response.post.PostResponseItem
import com.example.composelist.ui.theme.ComposeListTheme
import com.example.composelist.viewmodel.HomeViewModel


/**
 * Created by Jeetesh Surana.
 */

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeListTheme {
        PostCard(PostResponseItem("", 1, "test", 0)) {}
    }
}

@Composable
fun UserDataLists(
    activity: ComponentActivity,
    onClick: (userDataList: PostResponseItem?) -> Unit
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()

    val lazyPagingItems: LazyPagingItems<PostResponseItem> = homeViewModel.getUserDataWithPagination().collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState) {
        items(lazyPagingItems.itemCount) { userDataList ->
            PostCard(lazyPagingItems[userDataList]) { clickedUserDataList ->
                onClick(clickedUserDataList)
            }
        }
        manageAPIs(lazyPagingItems)
    }
}

private fun LazyListScope.manageAPIs(lazyPagingItems: LazyPagingItems<PostResponseItem>) {
    when (val state = lazyPagingItems.loadState.prepend) {
        is LoadState.NotLoading -> Unit
        is LoadState.Loading -> {
            Loading()
        }

        is LoadState.Error -> {
            Error(message = state.error.message ?: "")
        }
    }
    when (val state = lazyPagingItems.loadState.refresh) {
        is LoadState.NotLoading -> Unit
        is LoadState.Loading -> {
            Loading()
        }

        is LoadState.Error -> {
            Error(message = state.error.message ?: "")
        }
    }
    when (val state = lazyPagingItems.loadState.append) {
        is LoadState.NotLoading -> Unit
        is LoadState.Loading -> {
            Loading()
        }

        is LoadState.Error -> {
            Error(message = state.error.message ?: "")
        }
    }
}

private fun LazyListScope.Loading() {
    item {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}


private fun LazyListScope.Error(message: String) {
    item {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Show a Snackbar with the error message
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    // Add an action if needed
                }
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun PostCard(userDataList: PostResponseItem?, onClick: (userDataList: PostResponseItem?) -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    onClick(userDataList)
                }
                .background(Color.White)
                .fillMaxWidth(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.place_holder), // Placeholder image resource
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(64.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            )

            Column {
                Text(text = userDataList?.id.toString())
                Text(text = userDataList?.title ?: "")
            }
        }
    }
}