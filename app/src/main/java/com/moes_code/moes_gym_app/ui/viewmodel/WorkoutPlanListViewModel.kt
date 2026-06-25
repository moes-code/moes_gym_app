package com.moes_code.moes_gym_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moes_code.moes_gym_app.data.repository.GymRepository
import com.moes_code.moes_gym_app.model.WorkoutPlan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class WorkoutPlanListViewModel(repository: GymRepository) : ViewModel() {

    val plans: StateFlow<List<WorkoutPlan>> = repository.getAllPlans()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
