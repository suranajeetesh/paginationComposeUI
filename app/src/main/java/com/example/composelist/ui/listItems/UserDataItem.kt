package com.example.composelist.ui.listItems

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.composelist.R
import com.example.composelist.data.remote.model.response.userData.DataList
import com.example.composelist.ui.theme.ComposeListTheme
import com.example.composelist.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


/**
 * Created by Jeetesh Surana.
 */

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeListTheme {
        UserCard(DataList("", "test", "test", 0, "test")) {}
    }
}

@Composable
fun UserDataLists(
    activity: ComponentActivity,
    homeViewModel: HomeViewModel,
    onClick: (userDataList: DataList?) -> Unit
) {
    val lazyPagingItems: LazyPagingItems<DataList> = homeViewModel.getUserDataWithPagination().collectAsLazyPagingItems()

    LazyColumn(state = rememberLazyListState()) {
        items(lazyPagingItems.itemSnapshotList) { userDataList ->
            UserCard(userDataList) { clickedUserDataList ->
                onClick(clickedUserDataList)
            }
        }
    }

    LaunchedEffect(Unit) {
        activity.lifecycleScope.launch {
            homeViewModel.getData()
        }
    }
}


@Composable
fun UserCard(userDataList: DataList?, onClick: (userDataList: DataList?) -> Unit) {
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
                Text(text = userDataList?.first_name + " " + userDataList?.last_name)
                Text(text = userDataList?.email ?: "")
            }
        }
    }
}