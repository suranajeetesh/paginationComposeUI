package com.example.composelist.ui.screen// ProductDetailsScreen.kt

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.composelist.R
import com.example.composelist.data.remote.model.response.post.PostResponseItem
import com.example.composelist.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(postId: String, navController: NavHostController) {
    val viewModel = hiltViewModel<HomeViewModel>()

    // Fetch product details when the screen is initially displayed
    LaunchedEffect(postId) {
        viewModel.getPostData(postId)
    }

    // Observe product details from ViewModel
    val productDetailsState = remember(viewModel) {
        viewModel.postDetailsResponse
    }.collectAsState(initial = null)

    // Scaffold for basic layout structure
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            // Apply padding to the content
            Box(modifier = Modifier.padding(it)) {
                // Display product details if available
                productDetailsState.value?.let { productDetails ->
                    ProductDetailsContent(product = productDetails)
                } ?: run {
                    // Show loading indicator or error message if product details are not available
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    )
}

@Composable
fun ProductDetailsContent(product: PostResponseItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        // Image
        Image(
            painter = painterResource(id = R.drawable.place_holder),
            contentDescription = "Product Image",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillHeight
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row {// User ID
            Text(
                text = "User ID: ${product.userId}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(8.dp))
            // Post ID
            Text(
                text = "Post ID: ${product.id}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Title
        Text(
            text = "Title: ${product.title}",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Body
        Text(
            text = "Body: ${product.body}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


