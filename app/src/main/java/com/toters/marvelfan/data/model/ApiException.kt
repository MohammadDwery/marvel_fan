package com.toters.marvelfan.data.model

import com.toters.marvelfan.data.network.resposes.ErrorResponse
import java.io.IOException

data class ApiException(
    val error: ErrorResponse
) : IOException("${error.code} ${error.message}")
