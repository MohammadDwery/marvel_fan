package com.toters.marvelfan.data.model

class EventModel(
    id: Long,
    title: String,
    description: String,
    thumbnail: Thumbnail,
) : BaseModel(id, title, description, thumbnail)
