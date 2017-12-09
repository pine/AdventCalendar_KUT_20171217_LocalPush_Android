package moe.pine.adventcalendar.localpush

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Android 8 (Oreo) への対応
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "Default channel"
            manager.createNotificationChannel(channel)
        }

        // すぐに通知
        button_notify.setOnClickListener {
            val intent = MainActivity.createIntent(this)
            val contentIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)

            val notification = NotificationCompat.Builder(this, "default")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("れんちょんからのお知らせ")
                    .setContentText("にゃんぱすー")
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_renchon))
                    .build()

            val manager = NotificationManagerCompat.from(this)
            manager.notify(1, notification)
        }

        // 3秒後に通知
        button_notify_later.setOnClickListener {
            val intent = AlarmBroadcastReceiver.createIntent(this)
            val contentIntent = PendingIntent.getBroadcast(applicationContext, 1, intent, PendingIntent.FLAG_ONE_SHOT)

            val trigger = Calendar.getInstance()
            trigger.add(Calendar.SECOND, 3)

            val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            manager.setExact(AlarmManager.RTC_WAKEUP, trigger.timeInMillis, contentIntent)
        }
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, MainActivity::class.java)

    }
}
