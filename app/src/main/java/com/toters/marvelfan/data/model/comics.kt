package com.toters.marvelfan.data.model

data class Comics (
    val available: String,
    val returned: String,
    val collectionURI: String,
    val models: List<ComicsModel>
)

data class ComicsModel (
    val resourceURI: String,
    val name: String
)