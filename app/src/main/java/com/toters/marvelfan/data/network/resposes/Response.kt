package com.toters.marvelfan.data.network.resposes

class Response<T>(
    val status: String,
    val code: String,
    val data: Data<T>,
)

data class Data<T> (
    val offset: String,
    val limit: String,
    val total: String,
    val count: String,
    val results: List<T>
)
