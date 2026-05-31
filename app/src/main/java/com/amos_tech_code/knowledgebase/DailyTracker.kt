package com.amos_tech_code.knowledgebase

import android.content.Context
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

object DailyTracker {
    /**
     * Reloads the user's profile and logs an analytics event to track activity
     * for the Play Store 14-day requirement.
     */
    fun recordDailyVisit(context: Context) {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {
            // 1. Update Firebase Auth activity
            user.reload().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("DailyTracker", "Activity ping successful for: ${user.email}")
                }
            }

            // 2. Track specific activity in Firebase Analytics
            val analytics = FirebaseAnalytics.getInstance(context)
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            // This allows you to filter reports by user email
            analytics.setUserId(user.email)

            // Set a user property for "last_active_date"
            analytics.setUserProperty("last_active_date", today)

            // Log a specific "check_in" event
            val params =
                android.os.Bundle().apply {
                    putString("email", user.email)
                    putString("date", today)
                }
            analytics.logEvent("tester_check_in", params)
        }
    }
}
