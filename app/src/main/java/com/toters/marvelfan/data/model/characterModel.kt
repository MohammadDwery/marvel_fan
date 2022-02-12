package com.toters.marvelfan.data.model

data class CharacterModel (
    val id: String,
    val name: String,
    val description: String,
    val modified: String,
    val resourceURI: String,
    val urls: List<URL>,
    val thumbnail: Thumbnail,
    val comics: Comics,
    val stories: Stories,
    val events: Comics,
    val series: Comics
)

data class Thumbnail (
    val path: String,
    val extension: String
)

data class URL (
    val type: String,
    val url: String
)