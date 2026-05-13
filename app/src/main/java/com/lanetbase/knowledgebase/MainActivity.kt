package com.lanetbase.knowledgebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.lanetbase.knowledgebase.ui.KnowledgeBaseApp
import com.lanetbase.knowledgebase.ui.theme.KnowledgeBaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KnowledgeBaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    KnowledgeBaseApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}