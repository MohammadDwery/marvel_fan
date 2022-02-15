package com.toters.marvelfan.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toters.marvelfan.data.model.ApiException
import com.toters.marvelfan.data.network.DataResourceState
import com.toters.marvelfan.utils.NetworkConnectionInterceptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    inline fun <T> launchFetchItemAsync(
        crossinline execute: suspend () -> T,
    ): Flow<DataResourceState<T>> = flow {
        try {
            emit(DataResourceState.loading())
            val events = execute()
            emit(DataResourceState.success(events))
        } catch (e: ApiException) {
            emit(DataResourceState.failure(e.error.message))
        } catch (e: NetworkConnectionInterceptor.NoConnectionException) {
            emit(DataResourceState.failure(e.message))
        } catch (e: Exception) {
            emit(DataResourceState.failure("Something_went_wrong"))
        }
    }

    inline fun <T> launchFetchListAsync(
        crossinline execute: suspend () -> List<T>,
    ): Flow<DataResourceState<List<T>>> = flow {
        try {
            emit(DataResourceState.loading())
            val events = execute()
            if(events.isEmpty()) emit(DataResourceState.noResults())
            else emit(DataResourceState.success(events))
        } catch (e: ApiException) {
            emit(DataResourceState.failure(e.error.message))
        } catch (e: NetworkConnectionInterceptor.NoConnectionException) {
            emit(DataResourceState.failure(e.message))
        } catch (e: Exception) {
            emit(DataResourceState.failure("Something_went_wrong"))
        }
    }

    inline fun <T> launchPagingAsync(
        crossinline execute: suspend () -> Flow<T>,
        crossinline onSuccess: (Flow<T>) -> Unit,
        crossinline onError: (String) -> Unit,
        ) {
        viewModelScope.launch {
            try {
                val result = execute()
                onSuccess(result)
            } catch (ex: Exception) {
                onError(ex.message?:"Something went wrong")
            }
        }
    }
}