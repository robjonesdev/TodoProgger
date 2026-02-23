package com.robjonesdev.todoprogger.domain.reminders

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.robjonesdev.todoprogger.domain.models.TodoTask
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.ExperimentalTime

class AndroidReminderScheduler(private val context: Context) : ReminderScheduler {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @OptIn(ExperimentalTime::class)
    override fun schedule(task: TodoTask, dateTime: LocalDateTime) {
        val triggerTime = dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        Log.d("AndroidReminderScheduler", "Scheduling reminder for task ${task.id} at $dateTime")
        
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("EXTRA_TASK_ID", task.id)
            putExtra("EXTRA_TASK_TITLE", task.title)
            putExtra("EXTRA_TASK_DESCRIPTION", task.description)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmManager.canScheduleExactAlarms()) {
            Log.d("AndroidReminderScheduler", "Setting exact alarm")
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        } else {
            Log.d("AndroidReminderScheduler", "Setting inexact alarm")
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }

    override fun cancel(taskId: Int) {
        Log.d("AndroidReminderScheduler", "Canceling reminder for task $taskId")
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}

actual fun getReminderScheduler(context: Any?): ReminderScheduler {
    if (context == null || context !is Context) {
        throw IllegalArgumentException("AndroidReminderScheduler requires an Android Context")
    }
    return AndroidReminderScheduler(context)
}
