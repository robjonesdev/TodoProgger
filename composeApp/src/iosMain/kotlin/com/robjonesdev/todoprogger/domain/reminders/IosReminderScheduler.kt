package com.robjonesdev.todoprogger.domain.reminders

import com.robjonesdev.todoprogger.domain.models.TodoTask
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import platform.UserNotifications.*
import platform.Foundation.NSDate
import platform.Foundation.dateWithTimeIntervalSince1970
import kotlin.time.Duration

class IosReminderScheduler : ReminderScheduler {

    override fun schedule(task: TodoTask, dateTime: LocalDateTime) {
        val content = UNMutableNotificationContent().apply {
            setTitle(task.title)
            setBody(task.description)
            setSound(UNNotificationSound.defaultSound)
        }

        val triggerTime = dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        val now = platform.Foundation.NSDate().timeIntervalSince1970 * 1000
        val delaySeconds = ((triggerTime - now) / 1000).coerceAtLeast(1.0)

        val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
            delaySeconds,
            false
        )

        val request = UNNotificationRequest.requestWithIdentifier(
            task.id.toString(),
            content,
            trigger
        )

        UNUserNotificationCenter.currentNotificationCenter().apply {
            requestAuthorizationWithOptions(
                UNAuthorizationOptionAlert or UNAuthorizationOptionSound
            ) { granted, error ->
                if (granted) {
                    addNotificationRequest(request) { error ->
                        // Handle error if needed
                    }
                }
            }
        }
    }

    override fun cancel(taskId: Int) {
        UNUserNotificationCenter.currentNotificationCenter()
            .removePendingNotificationRequestsWithIdentifiers(listOf(taskId.toString()))
    }
}

actual fun getReminderScheduler(context: Any?): ReminderScheduler {
    return IosReminderScheduler()
}
