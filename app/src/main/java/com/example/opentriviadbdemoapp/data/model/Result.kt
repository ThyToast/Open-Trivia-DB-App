package com.example.opentriviadbdemoapp.data.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.opentriviadbdemoapp.utils.NetworkState

data class Result<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>,
    val refreshState: LiveData<NetworkState>,
    val refresh: () -> Unit,
    val retry: () -> Unit
)