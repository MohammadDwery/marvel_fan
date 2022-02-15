package com.toters.marvelfan.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterModel (
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
): Parcelable

@Parcelize
data class Thumbnail (
    val path: String,
    val extension: String
): Parcelable