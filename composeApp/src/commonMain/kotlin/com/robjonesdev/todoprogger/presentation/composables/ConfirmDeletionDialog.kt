package com.robjonesdev.todoprogger.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.robjonesdev.todoprogger.domain.models.TodoTask

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDeletionDialog(
    todoName: String,
    title: String = "Delete Todo?",
    description: String = "Are you certain you want to delete the Todo: \"${todoName}\"? This cannot be undone.",
    modifier: Modifier = Modifier,
    onRejectDeletion: () -> Unit,
    onConfirmDeletion: () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = onRejectDeletion,
        modifier = modifier,
        properties = DialogProperties(),
    ) {
        Surface(
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    TextButton(
                        onClick = { onConfirmDeletion() }
                    ) {
                        Text(text = "Yes, Delete It")
                    }
                    TextButton(
                        onClick = { onRejectDeletion() }
                    ) {
                        Text(text = "No, Keep It")
                    }
                }
            }
        }
    }
}