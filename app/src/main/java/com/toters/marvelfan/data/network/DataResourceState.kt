package com.toters.marvelfan.data.network

data class DataResourceState<T>(val status: DataResourceStatus?, val data : T?=null, val message: String?=null) {

    companion object{
        fun <T> success(data: T?): DataResourceState<T> = DataResourceState(
                status = DataResourceStatus.SUCCESS,
                data = data,
            )

        fun <T> noMoreResults(): DataResourceState<T> =
            DataResourceState(status = DataResourceStatus.NO_MORE_RESULTS,)

        fun <T> noResults(): DataResourceState<T> =
            DataResourceState(status = DataResourceStatus.NO_RESULTS)

        fun <T> loading(): DataResourceState<T> =
            DataResourceState(status = DataResourceStatus.LOADING)

        fun <T> failure(message: String?): DataResourceState<T> = DataResourceState(
                status = DataResourceStatus.FAILURE,
                message = message,
            )
    }
}
enum class DataResourceStatus {
    SUCCESS, FAILURE, NO_MORE_RESULTS, NO_RESULTS, LOADING
}