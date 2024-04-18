package com.example.composelist.ui.screen

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composelist.core.uI.BaseActivity
import com.example.composelist.ui.listItems.UserDataLists
import com.example.composelist.ui.theme.ComposeListTheme
import com.example.composelist.util.Constant.ArgumentsKeys.PRODUCT_DETAILS_ID
import com.example.composelist.util.Constant.ArgumentsKeys.PRODUCT_ID
import com.example.composelist.util.Constant.Routes.LIST_VIEW_ROUTE
import com.example.composelist.util.Constant.Routes.PRODUCT_DETAILS_ROUTE
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = LIST_VIEW_ROUTE) {
                        composable(LIST_VIEW_ROUTE) {
                            UserDataLists {
                                Toast.makeText(this@MainActivity, "${it?.title}", Toast.LENGTH_SHORT).show();
                                navController.navigate("$PRODUCT_DETAILS_ID${it?.id}")
                            }
                        }
                        composable(PRODUCT_DETAILS_ROUTE) { backStackEntry ->
                            val productId = backStackEntry.arguments?.getString(PRODUCT_ID)
                            if (!productId.isNullOrEmpty()) {
                                ProductDetailsScreen(productId,navController)
                            }
                        }
                    }
                }
            }
        }
    }
}



