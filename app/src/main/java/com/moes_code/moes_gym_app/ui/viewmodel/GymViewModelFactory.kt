package com.moes_code.moes_gym_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moes_code.moes_gym_app.data.repository.GymRepository

class GymViewModelFactory(
    private val repository: GymRepository,
    private val planId: Long? = null
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            WorkoutPlanListViewModel::class.java -> WorkoutPlanListViewModel(repository) as T
            WorkoutSessionViewModel::class.java -> WorkoutSessionViewModel(requireNotNull(planId) { "WorkoutSessionViewModel requires a non-null planId" }, repository) as T
            EditPlanViewModel::class.java -> EditPlanViewModel(requireNotNull(planId) { "EditPlanViewModel requires a non-null planId" }, repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
