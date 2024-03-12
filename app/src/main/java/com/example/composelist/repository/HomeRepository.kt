package com.example.composelist.repository

import android.content.Context
import com.example.composelist.data.remote.model.response.userData.UserDataResponse
import com.example.composelist.network.ApiRestService
import com.example.composelist.network.SafeApiRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class HomeRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val api: ApiRestService
) : SafeApiRequest(context) {

    suspend fun getUserData(pageCount:String?): UserDataResponse {
        return apiRequest { api.getUserData(pageCount) }
    }


}