package com.amos_tech_code.knowledgebase.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.amos_tech_code.knowledgebase.data.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class KnowledgeViewModel(application: Application) : AndroidViewModel(application) {
    private val preferenceManager = PreferenceManager(application)

    private val _userPoints = MutableStateFlow(0)
    val userPoints: StateFlow<Int> = _userPoints.asStateFlow()

    private val _dailyStreak = MutableStateFlow(0)
    val dailyStreak: StateFlow<Int> = _dailyStreak.asStateFlow()

    private val _hasClaimedDaily = MutableStateFlow(true)
    val hasClaimedDaily: StateFlow<Boolean> = _hasClaimedDaily.asStateFlow()

    val visitedItems = mutableStateListOf<Int>()
    val favorites = mutableStateListOf<Int>()
    val completedQuizzes = mutableStateListOf<Int>()

    init {

        viewModelScope.launch {
            launch {
                preferenceManager.userPointsFlow.collectLatest { points ->
                    _userPoints.value = points
                }
            }
            launch {
                preferenceManager.dailyStreakFlow.collectLatest { streak ->
                    _dailyStreak.value = streak
                }
            }
            launch {
                preferenceManager.visitedItemsFlow.collectLatest { items ->
                    visitedItems.clear()
                    visitedItems.addAll(items.map { it.toInt() })
                }
            }
            launch {
                preferenceManager.favoritesFlow.collectLatest { items ->
                    favorites.clear()
                    favorites.addAll(items.map { it.toInt() })
                }
            }
            launch {
                preferenceManager.completedQuizzesFlow.collectLatest { items ->
                    completedQuizzes.clear()
                    completedQuizzes.addAll(items.map { it.toInt() })
                }
            }
            launch {
                preferenceManager.lastVisitTimestampFlow.collectLatest { lastVisit ->
                    val today = System.currentTimeMillis() / (24 * 60 * 60 * 1000)
                    val lastVisitDay = lastVisit / (24 * 60 * 60 * 1000)
                    _hasClaimedDaily.value = today <= lastVisitDay
                }
            }
        }
    }

    fun earnPoints(points: Int) {
        viewModelScope.launch {
            val newPoints = _userPoints.value + points
            _userPoints.value = newPoints
            preferenceManager.savePoints(newPoints)
        }
    }

    fun claimDailyReward() {
        if (!_hasClaimedDaily.value) {
            earnPoints(50)
            val newStreak = _dailyStreak.value + 1
            _dailyStreak.value = newStreak
            _hasClaimedDaily.value = true
            viewModelScope.launch {
                preferenceManager.saveStreak(newStreak)
                preferenceManager.saveLastVisit(System.currentTimeMillis())
            }
        }
    }

    fun dismissDailyDialog() {
        _hasClaimedDaily.value = true
        // We don't save the visit here so they can claim it later if they just dismiss
    }

    fun toggleFavorite(itemId: Int) {
        viewModelScope.launch {
            preferenceManager.toggleFavorite(itemId.toString())
        }
    }

    fun markAsVisited(itemId: Int) {
        if (!visitedItems.contains(itemId)) {
            earnPoints(10)
            viewModelScope.launch {
                preferenceManager.toggleVisitedItem(itemId.toString())
            }
        }
    }

    fun completeQuiz(
        quizId: Int,
        points: Int,
    ) {
        if (!completedQuizzes.contains(quizId)) {
            earnPoints(points)
            viewModelScope.launch {
                preferenceManager.addCompletedQuiz(quizId.toString())
            }
        }
    }
}
