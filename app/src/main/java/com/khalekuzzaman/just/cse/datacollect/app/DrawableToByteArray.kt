package com.khalekuzzaman.just.cse.datacollect.app

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun drawableToByteArray(context: Context, drawableResId: Int): ByteArray {
    val drawable: Drawable? = context.getDrawable(drawableResId)
    val bitmap = if (drawable is BitmapDrawable) {
        drawable.bitmap
    } else {
        val width = if (!drawable?.bounds?.isEmpty()!!) drawable.bounds.width() else drawable.intrinsicWidth
        val height = if (!drawable.bounds.isEmpty()) drawable.bounds.height() else drawable.intrinsicHeight
        Bitmap.createBitmap(width.coerceAtLeast(1), height.coerceAtLeast(1), Bitmap.Config.ARGB_8888)
    }
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}


fun drawableToUri(context: Context, drawableResId: Int): Uri {
    val drawable: Drawable = context.getDrawable(drawableResId)!!
    val bitmap = if (drawable is BitmapDrawable) {
        drawable.bitmap
    } else {
        val width = if (drawable.bounds.isEmpty) drawable.intrinsicWidth else drawable.bounds.width()
        val height = if (drawable.bounds.isEmpty) drawable.intrinsicHeight else drawable.bounds.height()
        Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    }

    // Prepare a file to write the bitmap to.
    val imagesFolder = File(context.cacheDir, "images")
    imagesFolder.mkdirs()
    val file = File(imagesFolder, "shared_image.png")

    // Write the bitmap to the file.
    val stream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    stream.flush()
    stream.close()

    // Return a URI for the file.
    return Uri.fromFile(file)
}
