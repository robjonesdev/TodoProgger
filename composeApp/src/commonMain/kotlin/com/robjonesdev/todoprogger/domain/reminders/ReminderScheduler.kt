package com.robjonesdev.todoprogger.domain.reminders

import com.robjonesdev.todoprogger.domain.models.TodoTask
import kotlinx.datetime.LocalDateTime

/**
 * A platform-agnostic interface for scheduling and canceling reminders.
 */
interface ReminderScheduler {
    /**
     * Schedules a reminder for a given task to appear at a specific time.
     * @param task The task to be reminded of.
     * @param dateTime The date and time to show the notification.
     */
    fun schedule(task: TodoTask, dateTime: LocalDateTime)

    /**
     * Cancels any existing reminder for a given task ID.
     * @param taskId The ID of the task whose reminder should be canceled.
     */
    fun cancel(taskId: Int)
}

/**
 * Gets a platform-specific instance of [ReminderScheduler].
 */
expect fun getReminderScheduler(context: Any?): ReminderScheduler
