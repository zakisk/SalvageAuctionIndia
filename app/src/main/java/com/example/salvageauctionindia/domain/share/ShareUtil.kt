package com.example.salvageauctionindia.domain.share

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.salvageauctionindia.util.Constants.ADMIN_MOBILE_NO
import java.net.URLEncoder


object ShareUtil {

    fun shareAppLink(context: Context) {
        val appPackageName = context.packageName
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Check out the App at: https://play.google.com/store/apps/details?id=$appPackageName"
            )
            type = "text/plain"
        }

        context.startActivity(sendIntent)
    }

    fun shareOnWhatsapp(
        context: Context,
        message: String,
        mobileNo: String = ADMIN_MOBILE_NO
    ): Boolean {

        val intent = Intent(Intent.ACTION_VIEW).apply {
            `package` = "com.whatsapp"
            data = Uri.parse(
                "https://api.whatsapp.com/send?phone=$mobileNo&text=${
                    URLEncoder.encode(message, "UTF-8")
                }"
            )
        }
        return try {
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun callAdmin(context: Context, mobileNo: String = ADMIN_MOBILE_NO): Boolean {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$mobileNo")
        }

        return try {
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun sendEmail(context: Context, email: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, email)
        intent.putExtra(Intent.EXTRA_SUBJECT, "")
        intent.putExtra(Intent.EXTRA_TEXT, "")
        intent.type = "message/rfc822"
        context.startActivity(Intent.createChooser(intent, "Choose an Email Client"))
    }

}

