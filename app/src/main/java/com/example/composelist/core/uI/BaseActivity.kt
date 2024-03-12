package com.example.composelist.core.uI

import androidx.activity.ComponentActivity

/**
 * Created by JeeteshSurana.
 */

abstract class BaseActivity : ComponentActivity() {

    override fun onPause() {
//        hideKeyboard()
        super.onPause()
    }

    override fun onDestroy() {
//        hideKeyboard()
        super.onDestroy()
    }
}