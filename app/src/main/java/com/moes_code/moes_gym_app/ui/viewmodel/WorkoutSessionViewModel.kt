package com.moes_code.moes_gym_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moes_code.moes_gym_app.data.repository.GymRepository
import com.moes_code.moes_gym_app.model.Exercise
import com.moes_code.moes_gym_app.model.WorkoutPlanWithEntriesAndExercises
import com.moes_code.moes_gym_app.model.WorkoutSet
import com.moes_code.moes_gym_app.model.WorkoutSetTemplate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkoutSessionViewModel(
    private val planId: Long,
    private val repository: GymRepository
) : ViewModel() {

    val plan: StateFlow<WorkoutPlanWithEntriesAndExercises?> = repository
        .getPlanWithEntriesAndExercises(planId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _alternatives = MutableStateFlow<List<Exercise>>(emptyList())
    val alternatives: StateFlow<List<Exercise>> = _alternatives.asStateFlow()

    private val _templates = MutableStateFlow<List<WorkoutSetTemplate>>(emptyList())
    val templates: StateFlow<List<WorkoutSetTemplate>> = _templates.asStateFlow()

    init {
        viewModelScope.launch {
            repository.rotateExerciseIfNeeded(planId)
        }
    }

    fun loadAlternatives(exerciseId: Long) {
        viewModelScope.launch {
            _alternatives.value = repository.getAlternativesSortedByLastTrained(exerciseId).first()
        }
    }

    fun loadTemplates(exerciseId: Long) {
        viewModelScope.launch {
            _templates.value = repository.getTemplatesForExercise(exerciseId)
        }
    }

    fun selectAlternative(position: Int, newExerciseId: Long) {
        viewModelScope.launch {
            repository.selectAlternative(planId, position, newExerciseId)
        }
    }

    fun logSets(exerciseId: Long, sets: List<WorkoutSet>) {
        viewModelScope.launch {
            repository.logSets(exerciseId, sets)
        }
    }
}
