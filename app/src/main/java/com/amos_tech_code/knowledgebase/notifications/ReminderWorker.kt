package com.amos_tech_code.knowledgebase.notifications

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class ReminderWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val notificationHelper = NotificationHelper(applicationContext)
        notificationHelper.showReminderNotification(
            "Time for Daily Discovery! ✨",
            "Don't forget to claim your daily bonus and learn something new today."
        )
        return Result.success()
    }

    companion object {
        fun scheduleDailyReminder(context: Context) {
            val reminderRequest = PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
                .setInitialDelay(24, TimeUnit.HOURS) // Start tomorrow
                .addTag("daily_reminder")
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "daily_reminder",
                ExistingPeriodicWorkPolicy.KEEP,
                reminderRequest
            )
        }
    }
}
