package com.tipklemoa.tipkle.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.src.main.FeedDetailActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG: String = this.javaClass.simpleName
    var postId=0

    override fun onMessageReceived(remoteMessage: RemoteMessage)
    {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.notification != null)
        {
            if (remoteMessage.data["postId"] !=null){
                remoteMessage.data["postId"]?.let { Log.d("확인", it) }
                postId = remoteMessage.data["postId"]!!.toInt()
            }
            sendNotification(remoteMessage.notification?.title, remoteMessage.notification!!.body!!)
        }
    }

    override fun onNewToken(token: String)
    {
        Log.d(TAG, "Refreshed token : $token")
        super.onNewToken(token)
    }

    // 받은 알림을 기기에 표시하는 메서드
    private fun sendNotification(title: String?, body: String)
    {
        Log.d("postId", postId.toString())
        val intent = Intent(this, FeedDetailActivity::class.java)
        intent.putExtra("postId", postId)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val channelId = "my_channel"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 예외처리
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}