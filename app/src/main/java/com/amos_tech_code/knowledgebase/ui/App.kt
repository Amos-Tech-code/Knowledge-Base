package com.amos_tech_code.knowledgebase.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun KnowledgeBaseApp(
    modifier: Modifier = Modifier,
    viewModel: KnowledgeViewModel = viewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val userPoints by viewModel.userPoints.collectAsStateWithLifecycle()
    val dailyStreak by viewModel.dailyStreak.collectAsStateWithLifecycle()
    val hasClaimedDaily by viewModel.hasClaimedDaily.collectAsStateWithLifecycle()
    
    val visitedItems = viewModel.visitedItems
    val favorites = viewModel.favorites
    val completedQuizzes = viewModel.completedQuizzes

    // Daily Reward Dialog
    if (!hasClaimedDaily) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDailyDialog() },
            title = { Text("🌅 Daily Bonus!") },
            text = { Text("Welcome back! Here's 50 XP to start your day.") },
            confirmButton = {
                Button(onClick = {
                    viewModel.claimDailyReward()
                }) {
                    Text("Claim 50 XP")
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Enhanced Header with Gamification
        HeaderSection(
            userPoints = userPoints,
            dailyStreak = dailyStreak
        )

        // Tab Row
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Explore") },
                icon = { Text("🔍") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Daily Quiz") },
                icon = { Text("📝") }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text("Did You Know?") },
                icon = { Text("💡") }
            )
            Tab(
                selected = selectedTab == 3,
                onClick = { selectedTab = 3 },
                text = { Text("Stats") },
                icon = { Text("📊") }
            )
        }

        // Content based on selected tab
        when (selectedTab) {
            0 -> ExploreTab(
                knowledgeItems = DummyData.knowledgeItems,
                onPointsEarned = { viewModel.earnPoints(it) },
                visitedItems = visitedItems,
                favorites = favorites,
                onItemVisited = { viewModel.markAsVisited(it) },
                onToggleFavorite = { viewModel.toggleFavorite(it) }
            )
            1 -> QuizTab(
                quizzes = DummyData.quizzes,
                completedQuizzes = completedQuizzes,
                onQuizCompleted = { quizId, points ->
                    viewModel.completeQuiz(quizId, points)
                }
            )
            2 -> DidYouKnowTab(
                funFacts = DummyData.funFacts,
                onPointsEarned = { viewModel.earnPoints(it) }
            )
            3 -> StatsTab(
                userPoints = userPoints,
                dailyStreak = dailyStreak,
                visitedItems = visitedItems,
                favorites = favorites,
                completedQuizzes = completedQuizzes
            )
        }
    }
}

@Composable
fun HeaderSection(
    userPoints: Int,
    dailyStreak: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "🏆",
                    fontSize = 24.sp,
                    modifier = Modifier.animateContentSize()
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "$userPoints XP",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Knowledge Points",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "🔥",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$dailyStreak days",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Streak",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun ExploreTab(
    knowledgeItems: List<KnowledgeItem>,
    onPointsEarned: (Int) -> Unit,
    visitedItems: List<Int>,
    favorites: List<Int>,
    onItemVisited: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var showFavoritesOnly by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<KnowledgeItem?>(null) }

    val categories = listOf("All", "Technology", "Science", "History", "Arts", "Space", "Nature")

    val filteredItems = knowledgeItems.filter { item ->
        (selectedCategory == "All" || item.category == selectedCategory) &&
                (!showFavoritesOnly || favorites.contains(item.id))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Daily Featured Discovery
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "✨ Featured Discovery",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    "The Internet's Weight",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Did you know the entire internet weighs about as much as a strawberry?",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Jump to item */ },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Read Full Story (+20 XP)")
                }
            }
        }

        // Categories Scroll
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(category) },
                    leadingIcon = if (selectedCategory == category) {
                        { Text("✓") }
                    } else null
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = showFavoritesOnly,
                    onCheckedChange = { showFavoritesOnly = it }
                )
                Text("Favorites only")
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(
                    text = "${filteredItems.size} discoveries",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Knowledge Items
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filteredItems.forEach { item ->
                key(item.id) {
                    InteractiveKnowledgeCard(
                        item = item,
                        isFavorite = favorites.contains(item.id),
                        isVisited = visitedItems.contains(item.id),
                        onFavoriteClick = {
                            onToggleFavorite(item.id)
                        },
                        onItemClick = {
                            selectedItem = item
                            onItemVisited(item.id)
                        }
                    )
                }
            }
        }
    }

    // Detail Dialog
    if (selectedItem != null) {
        KnowledgeDetailDialog(
            item = selectedItem!!,
            onDismiss = { selectedItem = null },
            onShare = {
                onPointsEarned(2)
            }
        )
    }
}

@Composable
fun InteractiveKnowledgeCard(
    item: KnowledgeItem,
    isFavorite: Boolean,
    isVisited: Boolean,
    onFavoriteClick: () -> Unit,
    onItemClick: () -> Unit
) {
    var scale by remember { mutableStateOf(1f) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = if (isVisited)
                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f)
            else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isVisited) 2.dp else 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.emoji,
                        fontSize = 28.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Column {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = item.category,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Row {
                    if (isVisited) {
                        Text(
                            text = "✓",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    IconButton(
                        onClick = {
                            scale = 1.2f
                            onFavoriteClick()
                            scale = 1f
                        }
                    ) {
                        Text(
                            text = if (isFavorite) "★" else "☆",
                            fontSize = 24.sp,
                            color = if (isFavorite) Color(0xFFFFD700) else Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.description.take(100) + "...",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    scale = 0.95f
                    onItemClick()
                    scale = 1f
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Learn More (+10 XP)")
            }
        }
    }
}

@Composable
fun KnowledgeDetailDialog(
    item: KnowledgeItem,
    onDismiss: () -> Unit,
    onShare: () -> Unit
) {
    var showMoreFacts by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = item.emoji, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = item.title)
            }
        },
        text = {
            Column {
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (showMoreFacts) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "✨ Bonus: This fact has been viewed ${(100..1000).random()} times by other learners!",
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { showMoreFacts = !showMoreFacts },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (showMoreFacts) "Hide bonus" else "Show bonus fact")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onShare()
                    onDismiss()
                }
            ) {
                Text("Share (+2 XP)")
            }
        }
    )
}

@Composable
fun QuizTab(
    quizzes: List<Quiz>,
    completedQuizzes: List<Int>,
    onQuizCompleted: (Int, Int) -> Unit
) {
    var currentQuizIndex by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<Int?>(null) }
    var showResult by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }

    val currentQuiz = quizzes[currentQuizIndex]
    val isCompleted = completedQuizzes.contains(currentQuiz.id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress indicator - FIXED: removed lambda
        LinearProgressIndicator(
        progress = { (currentQuizIndex + 1) / quizzes.size.toFloat() },
        modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
        color = ProgressIndicatorDefaults.linearColor,
        trackColor = ProgressIndicatorDefaults.linearTrackColor,
        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Quiz header
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Quiz ${currentQuizIndex + 1}/${quizzes.size}",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${currentQuiz.points} XP",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Question
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Text(
                text = currentQuiz.question,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(24.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Answers
        currentQuiz.options.forEachIndexed { index, option ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .scale(if (selectedAnswer == index && !showResult) 1.02f else 1f)
                    .animateContentSize(animationSpec = tween(durationMillis = 300)),
                colors = CardDefaults.cardColors(
                    containerColor = when {
                        showResult && index == currentQuiz.correctAnswer ->
                            Color(0xFF4CAF50).copy(alpha = 0.3f)
                        showResult && selectedAnswer == index && index != currentQuiz.correctAnswer ->
                            Color(0xFFF44336).copy(alpha = 0.3f)
                        selectedAnswer == index ->
                            MaterialTheme.colorScheme.primaryContainer
                        else ->
                            MaterialTheme.colorScheme.surface
                    }
                ),
                onClick = {
                    if (!showResult) {
                        selectedAnswer = index
                    }
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${('A' + index)}.",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(30.dp)
                    )
                    Text(
                        text = option,
                        modifier = Modifier.weight(1f)
                    )
                    if (showResult && index == currentQuiz.correctAnswer) {
                        Text("✓", color = Color(0xFF4CAF50), fontSize = 20.sp)
                    } else if (showResult && selectedAnswer == index && index != currentQuiz.correctAnswer) {
                        Text("✗", color = Color(0xFFF44336), fontSize = 20.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action buttons
        if (!showResult) {
            Button(
                onClick = {
                    if (selectedAnswer != null) {
                        val correct = selectedAnswer == currentQuiz.correctAnswer
                        isCorrect = correct
                        showResult = true

                        if (correct && !isCompleted) {
                            onQuizCompleted(currentQuiz.id, currentQuiz.points)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedAnswer != null
            ) {
                Text("Check Answer")
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        selectedAnswer = null
                        showResult = false
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Try Again")
                }

                Button(
                    onClick = {
                        if (currentQuizIndex < quizzes.size - 1) {
                            currentQuizIndex++
                            selectedAnswer = null
                            showResult = false
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = currentQuizIndex < quizzes.size - 1
                ) {
                    Text("Next Quiz")
                }
            }
        }

        // Result message
        if (showResult) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isCorrect)
                        Color(0xFF4CAF50).copy(alpha = 0.1f)
                    else Color(0xFFF44336).copy(alpha = 0.1f)
                )
            ) {
                Text(
                    text = if (isCorrect)
                        "🎉 Correct! +${currentQuiz.points} XP"
                    else "❌ Not quite right. Keep learning!",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun DidYouKnowTab(
    funFacts: List<String>,
    onPointsEarned: (Int) -> Unit
) {
    var currentFactIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "💡",
                    fontSize = 80.sp,
                    modifier = Modifier.animateContentSize()
                )

                Spacer(modifier = Modifier.height(24.dp))

                AnimatedContent(
                    targetState = funFacts[currentFactIndex],
                    transitionSpec = {
                        (fadeIn() + slideInVertically()).togetherWith(fadeOut() + slideOutVertically())
                    },
                    label = "fact_animation"
                ) { fact ->
                    Text(
                        text = fact,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onPointsEarned(5) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("⭐ Collect Fact")
            }
            OutlinedButton(
                onClick = { onPointsEarned(2) },
                modifier = Modifier.weight(1f)
            ) {
                Text("📤 Share")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = {
                    if (currentFactIndex > 0) {
                        currentFactIndex--
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = currentFactIndex > 0
            ) {
                Text("← Previous")
            }

            Button(
                onClick = {
                    if (currentFactIndex < funFacts.size - 1) {
                        currentFactIndex++
                    } else {
                        currentFactIndex = 0
                    }
                    onPointsEarned(1)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (currentFactIndex < funFacts.size - 1) "Next →" else "Start Over")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Fact ${currentFactIndex + 1}/${funFacts.size}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun StatsTab(
    userPoints: Int,
    dailyStreak: Int,
    visitedItems: List<Int>,
    favorites: List<Int>,
    completedQuizzes: List<Int>
) {
    val level = (userPoints / 100) + 1
    val nextLevelPoints = level * 100
    val progressToNextLevel = (userPoints % 100) / 100f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
            // Level Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Level $level",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = progressToNextLevel,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .clip(RoundedCornerShape(6.dp))
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "$userPoints / $nextLevelPoints XP to next level",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Stats Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard("🔥", "Streak", "$dailyStreak days", Modifier.weight(1f))
                StatCard("📚", "Explored", "${visitedItems.size} facts", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard("⭐", "Favorites", "${favorites.size} items", Modifier.weight(1f))
                StatCard("📝", "Quizzes", "${completedQuizzes.size} done", Modifier.weight(1f))
            }

            // Achievements
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "🏆 Achievements",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    AchievementRow(
                        achieved = visitedItems.size >= 5,
                        title = "Curious Mind",
                        description = "Explore 5 facts"
                    )

                    AchievementRow(
                        achieved = favorites.size >= 3,
                        title = "Collector",
                        description = "Save 3 favorites"
                    )

                    AchievementRow(
                        achieved = completedQuizzes.size >= 3,
                        title = "Quiz Master",
                        description = "Complete 3 quizzes"
                    )

                    AchievementRow(
                        achieved = userPoints >= 100,
                        title = "Knowledge Seeker",
                        description = "Earn 100 XP"
                    )

                    AchievementRow(
                        achieved = dailyStreak >= 3,
                        title = "Consistent Learner",
                        description = "3-day streak"
                    )
                }
            }

            // Fun fact about their stats
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(
                    text = when {
                        visitedItems.isEmpty() -> "Start exploring to unlock more stats! 🚀"
                        visitedItems.size == 1 -> "You've taken your first step into knowledge! 🌟"
                        visitedItems.size < 5 -> "You're building your knowledge base! Keep going! 📚"
                        else -> "You're a knowledge explorer! ${visitedItems.size} facts and counting! 🌎"
                    },
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
    }
}

@Composable
fun StatCard(emoji: String, label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(emoji, fontSize = 24.sp)
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun AchievementRow(
    achieved: Boolean,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (achieved) "✅" else "⬜",
            fontSize = 20.sp,
            modifier = Modifier.width(32.dp)
        )
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = if (achieved) MaterialTheme.colorScheme.primary else Color.Gray
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}