package com.example.composelist.utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.composelist.repository.HomeRepository

class PassengerDataSourceFactory(private val dataRepository: HomeRepository) {
    val NETWORK_PAGE_SIZE = 6
    fun getPassengers() = Pager(config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
                                pagingSourceFactory = {UserDataPagingSource(dataRepository)}).liveData
}