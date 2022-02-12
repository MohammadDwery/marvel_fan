package com.toters.marvelfan.data.model

data class Stories (
    val available: String,
    val returned: String,
    val collectionURI: String,
    val items: List<StoryModel>
)

data class StoryModel (
    val resourceURI: String,
    val name: String,
    val type: String
)