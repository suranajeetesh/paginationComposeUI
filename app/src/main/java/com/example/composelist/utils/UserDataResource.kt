package com.example.composelist.utils

import com.example.composelist.data.remote.model.response.userData.DataList

sealed class UserDataResource {
//    val data = UserDataPagingSource(100, 50, listOf<DataList>())
    class Failure : UserDataResource()
    class Loading : UserDataResource()
    class Success : UserDataResource()
}