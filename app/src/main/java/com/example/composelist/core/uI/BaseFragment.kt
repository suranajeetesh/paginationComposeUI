package com.example.composelist.core.uI

import androidx.fragment.app.Fragment

/**
 * Created by JeeteshSurana.
 */

abstract class BaseFragment : Fragment() {
    override fun onPause() {
//        activity?.hideKeyboard()
        super.onPause()
    }

    override fun onDestroy() {
//        activity?.hideKeyboard()
        super.onDestroy()
    }
}