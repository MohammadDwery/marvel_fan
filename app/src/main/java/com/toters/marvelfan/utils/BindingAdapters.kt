package com.toters.marvelfan.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.toters.marvelfan.R
import com.toters.marvelfan.data.model.Thumbnail

@BindingAdapter("imageUrl")
fun ImageView.loadImage(thumbnail: Thumbnail?) {
    if (thumbnail==null) return
    Glide.with(this)
        .load("${thumbnail.path}.${thumbnail.extension}")
        .into(this)
}