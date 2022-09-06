package com.example.salvageauctionindia.data.remote

import com.example.salvageauctionindia.domain.notification.SalvageNotification
import com.example.salvageauctionindia.domain.workers.WorkerStarter
import com.example.salvageauctionindia.util.Constants.NOTIFY_REFRESHED_TOKEN_ID
import com.example.salvageauctionindia.util.Constants.USER_ID
import com.example.salvageauctionindia.util.SharedPreferencesUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class SalvageMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        message.data.let {
            val userId = SharedPreferencesUtil(this).get<String>(USER_ID)
            if (userId != null) {
                val notification = SalvageNotification(this)
                notification.sendNotification(
                    it["title"] ?: "",
                    it["body"] ?: "",
                    it["postId"] ?: "",
                    it["primeImage"] ?: "",
                    it["type"] ?: "",
                    notificationId = (it["postId"] ?: "N/A").hashCode()
                )
            }
        }
        super.onMessageReceived(message)
    }

    override fun onNewToken(tokenId: String) {
        SharedPreferencesUtil(this).put(NOTIFY_REFRESHED_TOKEN_ID, tokenId)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            WorkerStarter(this).startUpdateNotificationTokenWorker(userId, tokenId)
        }
        super.onNewToken(tokenId)
    }

}