package moe.pine.adventcalendar.localpush

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat

class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val newIntent = MainActivity.createIntent(context)
        val contentIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_ONE_SHOT)

        val notification = NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("れんちょんからのお知らせ")
                .setContentText("もう朝なのん")
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_renchon))
                .build()

        val manager = NotificationManagerCompat.from(context)
        manager.notify(2, notification)
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, AlarmBroadcastReceiver::class.java)
    }
}