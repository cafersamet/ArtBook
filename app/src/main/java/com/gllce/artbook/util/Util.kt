package com.gllce.artbook.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gllce.artbook.R

object Util {
    const val API_KEY = "23342739-37203ae815aae3cde0b2adad1"
    const val BASE_URL = "https://pixabay.com"
}


fun ImageView.downloadPath(
    path: String?,
    progressDrawable: CircularProgressDrawable
) {
    val options = RequestOptions.placeholderOf(progressDrawable)
        .error(R.drawable.ic_launcher_foreground)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(path)
        .into(this)
}

fun placeholderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadImage")
fun downloadSmallImage(view: ImageView, path: String?) {
    view.downloadPath(path, placeholderProgressBar(view.context))
}
