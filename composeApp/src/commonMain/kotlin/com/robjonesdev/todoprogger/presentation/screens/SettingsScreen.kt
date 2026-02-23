package com.robjonesdev.todoprogger.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.robjonesdev.todoprogger.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit,
    onBackTapped: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackTapped) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Theme",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ThemeColorItem(
                    color = GreenPrimary,
                    isSelected = selectedTheme == AppTheme.Green,
                    onClick = { onThemeSelected(AppTheme.Green) }
                )
                ThemeColorItem(
                    color = RedPrimary,
                    isSelected = selectedTheme == AppTheme.Red,
                    onClick = { onThemeSelected(AppTheme.Red) }
                )
                ThemeColorItem(
                    color = BluePrimary,
                    isSelected = selectedTheme == AppTheme.Blue,
                    onClick = { onThemeSelected(AppTheme.Blue) }
                )
                ThemeColorItem(
                    color = PurplePrimary,
                    isSelected = selectedTheme == AppTheme.Purple,
                    onClick = { onThemeSelected(AppTheme.Purple) }
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "App Information",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            ListItem(
                headlineContent = { Text("Version") },
                supportingContent = { Text("1.0.0") }
            )
            
            HorizontalDivider()
            
            ListItem(
                headlineContent = { Text("Developer") },
                supportingContent = { Text("Rob Jones") }
            )
        }
    }
}

@Composable
fun ThemeColorItem(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = if (isSelected) MaterialTheme.colorScheme.onSurface else Color.Transparent,
                shape = CircleShape
            )
            .clickable { onClick() }
    )
}
