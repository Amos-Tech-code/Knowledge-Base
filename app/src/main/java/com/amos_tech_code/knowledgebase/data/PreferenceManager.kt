package com.amos_tech_code.knowledgebase.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "knowledge_base_prefs")

class PreferenceManager(private val context: Context) {

    companion object {
        val USER_POINTS = intPreferencesKey("user_points")
        val DAILY_STREAK = intPreferencesKey("daily_streak")
        val LAST_VISIT_TIMESTAMP = longPreferencesKey("last_visit_timestamp")
        val VISITED_ITEMS = stringSetPreferencesKey("visited_items")
        val FAVORITES = stringSetPreferencesKey("favorites")
        val COMPLETED_QUIZZES = stringSetPreferencesKey("completed_quizzes")
    }

    val userPointsFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[USER_POINTS] ?: 0
    }

    val dailyStreakFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[DAILY_STREAK] ?: 0
    }

    val lastVisitTimestampFlow: Flow<Long> = context.dataStore.data.map { preferences ->
        preferences[LAST_VISIT_TIMESTAMP] ?: 0L
    }

    val visitedItemsFlow: Flow<Set<String>> = context.dataStore.data.map { preferences ->
        preferences[VISITED_ITEMS] ?: emptySet()
    }

    val favoritesFlow: Flow<Set<String>> = context.dataStore.data.map { preferences ->
        preferences[FAVORITES] ?: emptySet()
    }

    val completedQuizzesFlow: Flow<Set<String>> = context.dataStore.data.map { preferences ->
        preferences[COMPLETED_QUIZZES] ?: emptySet()
    }

    suspend fun savePoints(points: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_POINTS] = points
        }
    }

    suspend fun saveStreak(streak: Int) {
        context.dataStore.edit { preferences ->
            preferences[DAILY_STREAK] = streak
        }
    }

    suspend fun saveLastVisit(timestamp: Long) {
        context.dataStore.edit { preferences ->
            preferences[LAST_VISIT_TIMESTAMP] = timestamp
        }
    }

    suspend fun toggleVisitedItem(itemId: String) {
        context.dataStore.edit { preferences ->
            val currentSet = preferences[VISITED_ITEMS] ?: emptySet()
            val newSet = currentSet.toMutableSet()
            if (!newSet.contains(itemId)) {
                newSet.add(itemId)
            }
            preferences[VISITED_ITEMS] = newSet
        }
    }

    suspend fun toggleFavorite(itemId: String) {
        context.dataStore.edit { preferences ->
            val currentSet = preferences[FAVORITES] ?: emptySet()
            val newSet = currentSet.toMutableSet()
            if (newSet.contains(itemId)) {
                newSet.remove(itemId)
            } else {
                newSet.add(itemId)
            }
            preferences[FAVORITES] = newSet
        }
    }

    suspend fun addCompletedQuiz(quizId: String) {
        context.dataStore.edit { preferences ->
            val currentSet = preferences[COMPLETED_QUIZZES] ?: emptySet()
            val newSet = currentSet.toMutableSet()
            newSet.add(quizId)
            preferences[COMPLETED_QUIZZES] = newSet
        }
    }
}
