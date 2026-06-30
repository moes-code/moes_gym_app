package com.moes_code.moes_gym_app.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.moes_code.moes_gym_app.model.WorkoutPlanEntryWithExercise
import com.moes_code.moes_gym_app.model.WorkoutSet
import com.moes_code.moes_gym_app.model.WorkoutSetTemplate
import com.moes_code.moes_gym_app.ui.viewmodel.WorkoutSessionViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen(
    viewModel: WorkoutSessionViewModel,
    onBack: () -> Unit
) {
    val plan by viewModel.plan.collectAsState()
    val exerciseTemplates by viewModel.exerciseTemplates.collectAsState()
    val alternatives by viewModel.alternatives.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var showAlternatives by remember { mutableStateOf(false) }
    var altPosition by remember { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(plan?.plan?.name ?: "Training") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            return@Scaffold
        }

        val entries = plan?.entries ?: emptyList()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp)
        ) {
            items(entries) { entry ->
                val templates = exerciseTemplates[entry.entry.exerciseId] ?: emptyList()
                ExerciseCard(
                    entry = entry,
                    templates = templates,
                    onLog = { sets ->
                        viewModel.logSets(entry.entry.exerciseId, sets)
                        scope.launch { snackbarHostState.showSnackbar("Sets logged") }
                    },
                    onAlternatives = {
                        viewModel.loadAlternatives(entry.entry.exerciseId)
                        altPosition = entry.entry.position
                        showAlternatives = true
                    }
                )
            }
        }
    }

    if (showAlternatives) {
        val sheetState = rememberModalBottomSheetState()
        ModalBottomSheet(
            onDismissRequest = { showAlternatives = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            LazyColumn(modifier = Modifier.padding(bottom = 24.dp)) {
                items(alternatives) { exercise ->
                    ListItem(
                        headlineContent = { Text(exercise.name) },
                        supportingContent = {
                            Text(
                                exercise.lastTrainedAt?.let {
                                    "Last trained: ${formatTimestamp(it)}"
                                } ?: "Never trained"
                            )
                        },
                        modifier = Modifier.clickable {
                            viewModel.selectAlternative(altPosition, exercise.id)
                            showAlternatives = false
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
}

@Composable
private fun ExerciseCard(
    entry: WorkoutPlanEntryWithExercise,
    templates: List<WorkoutSetTemplate>,
    onLog: (List<WorkoutSet>) -> Unit,
    onAlternatives: () -> Unit
) {
    val exercise = entry.exercise
    var showWeight by remember { mutableStateOf(false) }

    val defaultSets = if (templates.isNotEmpty()) {
        templates.map { EditableSet(it.setNumber, restTime = it.restTime) }
    } else {
        val count = exercise.defaultSets ?: 1
        (1..count).map { EditableSet(it, restTime = exercise.defaultRestTime ?: 120) }
    }
    var sets by remember(exercise.id, templates) { mutableStateOf(defaultSets) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onAlternatives) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Alternatives",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            exercise.note?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (exercise.isBodyweight) {
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = showWeight,
                        onCheckedChange = { showWeight = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Added weight",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            val showReps = exercise.defaultReps != null || templates.any { it.reps != null }

            sets.forEachIndexed { i, set ->
                ExerciseSetRow(
                    set = set,
                    showWeightField = !exercise.isBodyweight || showWeight,
                    showRepsField = showReps,
                    onWeightChange = { newVal ->
                        sets = sets.toMutableList().also { it[i] = it[i].copy(weight = newVal) }
                    },
                    onRepsChange = { newVal ->
                        sets = sets.toMutableList().also { it[i] = it[i].copy(reps = newVal) }
                    },
                    onDurationChange = { newVal ->
                        sets = sets.toMutableList().also {
                            it[i] = it[i].copy(durationSeconds = newVal)
                        }
                    }
                )
                if (i < sets.lastIndex) Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    val loggedSets = sets.filter { it.isFilled }.map { it.toWorkoutSet(exercise.id) }
                    if (loggedSets.isNotEmpty()) {
                        onLog(loggedSets)
                        sets = defaultSets
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text("Log All Sets")
            }
        }
    }
}

@Composable
private fun ExerciseSetRow(
    set: EditableSet,
    showWeightField: Boolean,
    showRepsField: Boolean,
    onWeightChange: (String) -> Unit,
    onRepsChange: (String) -> Unit,
    onDurationChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Set ${set.number}",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.width(40.dp)
        )

        if (showWeightField) {
            OutlinedTextField(
                value = set.weight,
                onValueChange = onWeightChange,
                label = { Text("kg") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.width(72.dp),
                colors = outlinedFieldColors()
            )
        }

        if (showRepsField) {
            OutlinedTextField(
                value = set.reps,
                onValueChange = onRepsChange,
                label = { Text("reps") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.width(72.dp),
                colors = outlinedFieldColors()
            )
        } else {
            OutlinedTextField(
                value = set.durationSeconds,
                onValueChange = onDurationChange,
                label = { Text("sec") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.width(72.dp),
                colors = outlinedFieldColors()
            )
        }

        Text(
            text = if (set.restTime >= 60) "${set.restTime / 60} min" else "${set.restTime}s",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun outlinedFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    focusedLabelColor = MaterialTheme.colorScheme.primary,
    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
    cursorColor = MaterialTheme.colorScheme.primary
)

private data class EditableSet(
    val number: Int,
    val weight: String = "",
    val reps: String = "",
    val durationSeconds: String = "",
    val restTime: Int = 120
) {
    val isFilled: Boolean
        get() = weight.isNotBlank() || reps.isNotBlank() || durationSeconds.isNotBlank()

    fun toWorkoutSet(exerciseId: Long) = WorkoutSet(
        exerciseId = exerciseId,
        timestamp = System.currentTimeMillis(),
        number = number,
        weight = weight.toDoubleOrNull(),
        reps = reps.toIntOrNull(),
        restTime = restTime,
        durationSeconds = durationSeconds.toLongOrNull()
    )
}

private fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
