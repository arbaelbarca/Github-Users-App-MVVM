package com.arbaelbarca.githublistapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.loadImageUrl(url: String, context: Context) {
    Glide.with(context)
        .load(url)
//        .error(R.drawable.no_image)
//        .placeholder(R.drawable.no_image)
        .into(this)
}

