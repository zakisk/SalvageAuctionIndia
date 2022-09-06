package com.example.salvageauctionindia.util


import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Base64
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.salvageauctionindia.BuildConfig
import com.example.salvageauctionindia.data.remote.dtos.*
import com.example.salvageauctionindia.domain.model.SparePart
import com.google.gson.Gson
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import me.shouheng.compress.Compress
import me.shouheng.compress.concrete
import me.shouheng.compress.strategy.config.ScaleMode
import java.io.ByteArrayOutputStream
import java.io.File
import java.math.BigInteger
import java.net.MalformedURLException
import java.net.URL
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

val Any.TAG: String
    get() = this::class.java.name

fun Any?.isNotNull() = this != null

internal fun Context.showToast(message: String) {
        MainScope().launch {
            Toast.makeText(
                this@showToast,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

fun String.asSHA256(): String {
    val md = MessageDigest.getInstance("SHA-256")
    return BigInteger(1, md.digest(toByteArray()))
        .toString(16).padStart(16, '0')
}

/** Get uri of file. */
fun File.asUri(ctx: Context): Uri {
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
        FileProvider.getUriForFile(ctx, BuildConfig.APPLICATION_ID + ".provider", this)
    } else {
        Uri.fromFile(this)
    }
}


fun Uri.asBitmap(context: Context): Bitmap? {
    return Compress.with(context, this)
        .setQuality(100)
        .concrete {
            withScaleMode(ScaleMode.SCALE_LARGER)
            withIgnoreIfSmaller(true)
        }
        .getBitmap()
}


fun Bitmap?.asBase64(): String? {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this?.compress(
        Bitmap.CompressFormat.JPEG,
        100,
        byteArrayOutputStream
    )
    val imageBytes = byteArrayOutputStream.toByteArray()
//    val prefix = "data:${getType(uri, contentResolver) ?: "image/*"};base64,"
    return Base64.encodeToString(imageBytes, Base64.DEFAULT)
}


fun Boolean.asTinyInt(): Int = if (this) 1 else 0

fun Int.asBoolean(): Boolean = this == 1


fun NotificationTokenDto.asJSON(): String {
    val gson = Gson()
    return gson.toJson(this)
}

fun String.asNotificationTokenDto() : NotificationTokenDto {
    val gson = Gson()
    return gson.fromJson(this, NotificationTokenDto::class.java)
}


fun String.asUrl() : URL? {
    return try {
        URL(this)
    } catch (e: MalformedURLException) {
        null
    }
}


fun VehicleDto.asJSON(): String {
    val gson = Gson()
    return gson.toJson(this)
}

fun String.asVehicleDto(): VehicleDto {
    val gson = Gson()
    return gson.fromJson(this, VehicleDto::class.java)
}


fun SparePartDto.asJSON(): String {
    val gson = Gson()
    return gson.toJson(this)
}


fun String.asSparePartDto(): SparePartDto {
    val gson = Gson()
    return gson.fromJson(this, SparePartDto::class.java)
}


fun Date.toFormattedString() : String {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return formatter.format(this)
}


fun List<Uri>.asStringArray() : Array<String> {
    return this.map { it.toString() }.toTypedArray()
}



fun UserDto.asJSON() : String {
    val gson = Gson()
    return gson.toJson(this)
}

fun String.asUserDto() : UserDto {
    val gson = Gson()
    return gson.fromJson(this, UserDto::class.java)
}


fun UpdateBody.asJSON() : String {
    val gson = Gson()
    return gson.toJson(this)
}



fun String.asUpdateBody() : UpdateBody {
    val gson = Gson()
    return gson.fromJson(this, UpdateBody::class.java)
}



fun SparePart.asDTO() : SparePartDto {
    return SparePartDto(
        userId = userId,
        postId = postId,
        postDate = postDate,
        brandName = brandName,
        title = title,
        description = description,
        ownerNumber = ownerNumber,
        address = address,
        price = price,
        isApproved = isApproved.asTinyInt(),
        isSold = isSold.asTinyInt(),
        imagePrefix = imagePrefix,
        primeImage = primeImage,
        noOfImages = noOfImages,
    )
}


fun SparePartDto.asDomain() : SparePart {
    return SparePart(
        userId = userId,
        postId = postId,
        postDate = postDate,
        brandName = brandName,
        title = title,
        description = description,
        ownerNumber = ownerNumber,
        address = address,
        price = price,
        isApproved = isApproved.asBoolean(),
        isSold = isSold.asBoolean(),
        imagePrefix = imagePrefix,
        primeImage = primeImage,
        noOfImages = noOfImages,
    )
}