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
import com.example.composelist.core.uI.BaseActivity
import com.example.composelist.data.remote.model.response.userData.DataList
import com.example.composelist.ui.listItems.PostCard
import com.example.composelist.ui.listItems.UserDataLists
import com.example.composelist.ui.theme.ComposeListTheme
import com.example.composelist.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


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

                    UserDataLists(this) {
                        Log.e("TAG","onCreate() --> ${it?.title}")
                        Toast.makeText(this, "${it?.title}", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private fun initObserver() {

    }

}

