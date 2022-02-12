package com.toters.marvelfan.data.network

data class DataResourceState<T>(val status: Status?, val data : T?, val message: String?) {

    companion object{
        fun <T> success(data: T?): DataResourceState<T> {
            return DataResourceState(
                Status.SUCCESS,
                data,
                null
            )
        }
        fun <T> noMoreResults(): DataResourceState<T> {
            return DataResourceState(
                Status.NO_MORE_RESULTS,
                null,
                null
            )
        }
        fun <T> noResults(): DataResourceState<T> {
            return DataResourceState(
                Status.NO_RESULTS,
                null,
                null
            )
        }
        fun <T> noInternet(): DataResourceState<T> {
            return DataResourceState(
                Status.NO_INTERNET,
                null,
                null
            )
        }
        fun <T> loading(): DataResourceState<T>? {
            return DataResourceState(
                Status.LOADING,
                null,
                null
            )
        }

        fun <T> failure(): DataResourceState<T> {
            return DataResourceState(
                Status.FAILURE,
                null,
                null
            )
        }
    }

    enum class Status {
        SUCCESS, FAILURE, NO_MORE_RESULTS, NO_RESULTS, NO_INTERNET, LOADING
    }
}