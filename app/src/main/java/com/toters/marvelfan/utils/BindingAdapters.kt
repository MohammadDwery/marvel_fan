package com.toters.marvelfan.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.toters.marvelfan.R
import com.toters.marvelfan.data.model.Thumbnail

@BindingAdapter("imageUrl")
fun ImageView.loadImage(thumbnail: Thumbnail?) {
    Glide.with(this)
        .load("${thumbnail?.path}.${thumbnail?.extension}")
        .placeholder(R.color.secondary)
        .error(android.R.color.holo_red_light)
        .centerCrop()
        .transform(RoundedCorners(context.resources.getDimension(R.dimen.dp_32).toInt()))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}