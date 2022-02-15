package com.toters.marvelfan.data.model

class ComicsModel(
    id: Long,
    title: String,
    description: String,
    thumbnail: Thumbnail,
) : BaseModel(id, title, description, thumbnail)
