package com.moes_code.moes_gym_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moes_code.moes_gym_app.data.repository.GymRepository
import com.moes_code.moes_gym_app.model.Exercise
import com.moes_code.moes_gym_app.model.WorkoutPlanWithEntriesAndExercises
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditPlanViewModel(
    private val planId: Long,
    private val repository: GymRepository
) : ViewModel() {

    val plan: StateFlow<WorkoutPlanWithEntriesAndExercises?> = repository
        .getPlanWithEntriesAndExercises(planId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val allExercises: StateFlow<List<Exercise>> = repository.getAllExercises()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _planName = MutableStateFlow("")
    val planName: StateFlow<String> = _planName.asStateFlow()

    private val _planDescription = MutableStateFlow("")
    val planDescription: StateFlow<String> = _planDescription.asStateFlow()

    init {
        viewModelScope.launch {
            val p = repository.getPlanWithEntriesAndExercises(planId).first()
            p?.plan?.let {
                _planName.value = it.name
                _planDescription.value = it.description ?: ""
            }
        }
    }

    fun updatePlanName(name: String) {
        _planName.value = name
    }

    fun updatePlanDescription(description: String) {
        _planDescription.value = description
    }

    fun savePlanDetails() {
        viewModelScope.launch {
            val p = plan.value?.plan ?: return@launch
            repository.updateWorkoutPlan(
                p.copy(
                    name = _planName.value,
                    description = _planDescription.value.ifBlank { null }
                )
            )
        }
    }

    fun addExercise(exerciseId: Long) {
        viewModelScope.launch {
            repository.addExerciseToPlan(planId, exerciseId)
        }
    }

    fun removeExercise(position: Int) {
        viewModelScope.launch {
            repository.removeExerciseFromPlan(planId, position)
        }
    }
}
