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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.robjonesdev.todoprogger.presentation.theme.*
import org.jetbrains.compose.resources.stringResource
import todoprogger.composeapp.generated.resources.Res
import todoprogger.composeapp.generated.resources.*

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
                title = { Text(stringResource(Res.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackTapped) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.action_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
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
                text = stringResource(Res.string.theme_section),
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
                text = stringResource(Res.string.app_info_section),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Column {
                    ListItem(
                        headlineContent = { Text(stringResource(Res.string.version_label)) },
                        supportingContent = { Text("1.0.0") },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                    )
                    
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outlineVariant)
                    
                    ListItem(
                        headlineContent = { Text(stringResource(Res.string.developer_label)) },
                        supportingContent = { Text(stringResource(Res.string.developer_name)) },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                    )
                }
            }
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
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = CircleShape
            )
            .padding(if (isSelected) 4.dp else 0.dp)
            .clip(CircleShape)
            .clickable { onClick() }
    )
}
