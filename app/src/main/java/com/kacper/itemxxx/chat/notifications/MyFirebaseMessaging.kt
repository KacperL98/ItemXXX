package com.kacper.itemxxx.chat.notifications

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kacper.itemxxx.chat.chatsActivity.MessageChatActivity
import com.kacper.itemxxx.mainPanel.PanelActivity

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessaging : FirebaseMessagingService() {

    override fun onMessageReceived(mRemoteMessage: RemoteMessage) {
        super.onMessageReceived(mRemoteMessage)

        val sent = mRemoteMessage.data["sented"]
        val user = mRemoteMessage.data["user"]
        val sharePref = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val currentOnlineUser = sharePref.getString("currentUser", "none")

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser!=null && sent == firebaseUser.uid)
        {
            if (currentOnlineUser != user){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                    sendOreoNotifications(mRemoteMessage)
                }else{
                    sendNotifications(mRemoteMessage)
                }
            }
        }
    }
    private fun sendNotifications(remoteMessage: RemoteMessage) {

        val user = remoteMessage.data["user"]
        val icon = remoteMessage.data["icon"]
        val title =remoteMessage.data["title"]
        val body = remoteMessage.data["body"]

        val notification = remoteMessage.notification
        val j = user!!.replace("[\\D]".toRegex(), "").toInt()
        val intent = Intent(this, PanelActivity::class.java)

        val bundle = Bundle()
        bundle.putString("userid", user)
        intent.putExtras(bundle)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            .setSmallIcon(icon!!.toInt())
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSound)
            .setContentIntent(pendingIntent)

        val notify = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        var i = 0
        if (j>0){
            i=j
        }

        notify.notify(i, builder.build())
    }
    private fun sendOreoNotifications(mRemoteMessage: RemoteMessage) {
        val user = mRemoteMessage.data["user"]
        val icon = mRemoteMessage.data["icon"]
        val title = mRemoteMessage.data["title"]
        val body = mRemoteMessage.data["body"]

        val notification = mRemoteMessage.notification
        val j = user!!.replace("[\\D]".toRegex(), "").toInt()
        val intent = Intent(this, PanelActivity::class.java)

        val bundle = Bundle()
        bundle.putString("userid", user)
        intent.putExtras(bundle)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val oreoNotification = OreoNotification(this)

        val builder: Notification.Builder = oreoNotification.getOreoNotification(title, body, pendingIntent, defaultSound, icon)
        var i = 0
        if (j>0){
            i=j
        }
        oreoNotification.getManager!!.notify(i, builder.build())
    }
}