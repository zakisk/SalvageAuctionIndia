package com.example.salvageauctionindia.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.*
import java.net.HttpURLConnection


class ImageDownloader(private val context: Context) {


    fun download(urlString: String, imageName: String) {
        CoroutineScope(IO).launch {
            val bitmap = loadBitmap(urlString)
            if (bitmap != null) {
                saveMediaToStorage(imageName, bitmap)
            }
        }
    }


    fun share(urlString: String, imageName: String) {
        CoroutineScope(IO).launch {
            val bitmap = loadBitmap(urlString)
            if (bitmap != null) {
                val uri = saveMediaToStorage(imageName, bitmap, showToast = false)
                if (uri != null) {
                    shareImage(uri)
                }
            }
        }
    }


    private fun shareImage(uri: Uri) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/jpg"
        context.startActivity(intent)
    }


    fun loadBitmap(urlString: String): Bitmap? {
        val url = urlString.asUrl()
        if (url != null) {
            val connection: HttpURLConnection?
            try {
                connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val inputStream: InputStream = connection.inputStream
                val bufferedInputStream = BufferedInputStream(inputStream)
                return BitmapFactory.decodeStream(bufferedInputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            context.showToast("Unable to download image Url is incorrect")
        }
        return null
    }


    private fun saveMediaToStorage(imageName: String, bitmap: Bitmap?, showToast: Boolean = true): Uri? {
        val filename = "$imageName.jpg"
        var fos: OutputStream? = null
        var uri: Uri? = null
        var savedPath = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
                if (imageUri != null) {
                    uri = imageUri
                    savedPath = imageUri.path + "/$filename"
                }
            }
        } else {
            val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            savedPath = image.absolutePath
            uri = image.asUri(context)
            fos = FileOutputStream(image)
        }

        if (fos != null) {
            if (bitmap != null) {
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)) {
                    if (showToast) {
                        context.showToast("Saved to $savedPath")
                    }
                }
            } else {
                context.showToast("Unable to download image")
            }
        } else {
            context.showToast("Unable to save image to your Storage")
        }
        return uri
    }
}