package com.example.lista4_2

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageItem(val image: Bitmap, val imageName: String) : Parcelable

