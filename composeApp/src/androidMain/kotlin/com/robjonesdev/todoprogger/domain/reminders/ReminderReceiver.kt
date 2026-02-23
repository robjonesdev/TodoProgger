package com.robjonesdev.todoprogger.domain.reminders

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val taskId = intent?.getIntExtra("EXTRA_TASK_ID", -1) ?: -1
        val title = intent?.getStringExtra("EXTRA_TASK_TITLE") ?: "Todo Reminder"
        val description = intent?.getStringExtra("EXTRA_TASK_DESCRIPTION") ?: ""

        if (context != null && taskId != -1) {
            showNotification(context, taskId, title, description)
        }
    }

    private fun showNotification(context: Context, taskId: Int, title: String, description: String) {
        val channelId = "todo_reminders"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Todo Reminders",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(taskId, notification)
    }
}
