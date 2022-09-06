package com.example.salvageauctionindia.domain.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.ui.MainActivity
import com.example.salvageauctionindia.util.Constants.BASE_URL
import com.example.salvageauctionindia.util.Constants.IS_ACTIVITY_RUNNING
import com.example.salvageauctionindia.util.Constants.NOTIFY_TYPE_VEHICLE
import com.example.salvageauctionindia.util.ImageDownloader
import com.example.salvageauctionindia.util.SharedPreferencesUtil

class SalvageNotification(
    private val context: Context,
    private val isProgressive: Boolean = false
) {

    private var maxProgress = 100
    private var currentProgress = 0

    private val notifyId = 9

    private var notificationBuilder: NotificationCompat.Builder =
        NotificationCompat.Builder(context.applicationContext, context.packageName)

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun sendNotification(
        title: String,
        messageBody: String,
        postId: String? = null,
        primeImage: String? = null,
        type: String? = null,
        notificationId: Int = notifyId
    ) {
        val uri = if (type == NOTIFY_TYPE_VEHICLE) "https://salvageauctionindia.com/vehicles/$postId"
        else "https://salvageauctionindia.com/spare-parts/$postId"

        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(uri),
            context,
            MainActivity::class.java
        )
        val isActivityRunning =
            SharedPreferencesUtil(context).get<Boolean>(IS_ACTIVITY_RUNNING) ?: false
        if (!isActivityRunning) {
            deepLinkIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }


        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_IMMUTABLE
        }

        val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(deepLinkIntent)
            getPendingIntent(0, flag)
        }

        val defaultSoundUri: Uri = Uri.parse("android.resource://${context.packageName}/${R.raw.car_sound}")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                context.packageName,
                context.packageName,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = context.packageName
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
            channel.setSound(defaultSoundUri, audioAttributes)
            notificationManager.createNotificationChannel(channel)
        }

        notificationBuilder.apply {
            setSmallIcon(R.drawable.ic_sell)
            setContentTitle(title)
            setContentText(messageBody)
            setAutoCancel(true)
            setSound(defaultSoundUri)
            setTicker("New Item Added")
            setCategory(NotificationCompat.CATEGORY_ALARM)
            setChannelId(context.packageName)
            color = context.getColor(R.color.apricot)
            if (notificationId != notifyId) {
                val url = "${BASE_URL}get-image/$postId/$primeImage"
                val bitmap = ImageDownloader(context).loadBitmap(url)
                setStyle(NotificationCompat
                    .BigPictureStyle()
                    .bigPicture(bitmap)
                )

                setContentIntent(deepLinkPendingIntent)
                priority = NotificationCompat.PRIORITY_HIGH
            }
            if (isProgressive) {
                setProgress(maxProgress, currentProgress, false)
                setOngoing(true)
            }
        }

        notificationManager.notify(notificationId, notificationBuilder.build())
    }


    fun updateProgress(current: Int, isClearNotification: Boolean = false) {
        currentProgress = if (current == -1) {
            notificationBuilder
                .setOngoing(false)
                .setContentTitle("Completed")
            100
        } else {
            current
        }

        notificationBuilder
            .setProgress(maxProgress, currentProgress, false)
            .setSilent(true)
        notificationManager.notify(notifyId, notificationBuilder.build())
        if (isClearNotification)
            notificationManager.cancel(notifyId)
    }

    fun cancelNotification() {
        notificationManager.cancel(notifyId)
    }

}