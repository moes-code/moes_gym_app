package com.moes_code.moes_gym_app.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moes_code.moes_gym_app.model.Exercise
import com.moes_code.moes_gym_app.model.WorkoutPlanEntryWithExercise
import com.moes_code.moes_gym_app.ui.viewmodel.EditPlanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPlanScreen(
    viewModel: EditPlanViewModel,
    onBack: () -> Unit
) {
    val plan by viewModel.plan.collectAsState()
    val allExercises by viewModel.allExercises.collectAsState()
    val planName by viewModel.planName.collectAsState()
    val planDescription by viewModel.planDescription.collectAsState()

    var showExercisePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(plan?.plan?.name ?: "Edit Plan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = planName,
                    onValueChange = { viewModel.updatePlanName(it) },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = planDescription,
                    onValueChange = { viewModel.updatePlanDescription(it) },
                    label = { Text("Description (optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        viewModel.savePlanDetails()
                        onBack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Save")
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Exercises",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            val entries = plan?.entries ?: emptyList()
            items(entries) { entry ->
                ExerciseEntryCard(
                    entry = entry,
                    onRemove = { viewModel.removeExercise(entry.entry.position) }
                )
            }

            item {
                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { showExercisePicker = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Add Exercise")
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }

    if (showExercisePicker) {
        val existingIds = (plan?.entries ?: emptyList()).map { it.exercise.id }.toSet()
        ExercisePickerSheet(
            exercises = allExercises,
            existingIds = existingIds,
            onAdd = { exerciseId ->
                viewModel.addExercise(exerciseId)
                showExercisePicker = false
            },
            onDismiss = { showExercisePicker = false }
        )
    }
}

@Composable
private fun ExerciseEntryCard(
    entry: WorkoutPlanEntryWithExercise,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = entry.exercise.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Remove exercise",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExercisePickerSheet(
    exercises: List<Exercise>,
    existingIds: Set<Long>,
    onAdd: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Text(
            text = "Add Exercise",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
        LazyColumn(modifier = Modifier.padding(bottom = 24.dp)) {
            items(exercises) { exercise ->
                val inPlan = exercise.id in existingIds
                val alpha = if (inPlan) 0.5f else 1f
                ListItem(
                    headlineContent = {
                        Text(
                            exercise.name,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)
                        )
                    },
                    trailingContent = {
                        if (inPlan) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                        }
                    },
                    modifier = if (!inPlan) {
                        Modifier.clickable { onAdd(exercise.id) }
                    } else {
                        Modifier
                    }
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    thickness = 0.5.dp
                )
            }
        }
    }
}
