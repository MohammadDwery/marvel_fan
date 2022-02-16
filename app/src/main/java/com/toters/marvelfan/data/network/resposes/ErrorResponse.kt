package com.toters.marvelfan.data.network.resposes

data class ErrorResponse(
    val code: String,
    val message: String?=null,
    val status: String?=null
)