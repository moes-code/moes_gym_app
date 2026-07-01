package com.moes_code.moes_gym_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.moes_code.moes_gym_app.data.repository.GymRepository
import com.moes_code.moes_gym_app.ui.screen.EditPlanScreen
import com.moes_code.moes_gym_app.ui.screen.PlanListScreen
import com.moes_code.moes_gym_app.ui.screen.SessionScreen
import com.moes_code.moes_gym_app.ui.viewmodel.EditPlanViewModel
import com.moes_code.moes_gym_app.ui.viewmodel.GymViewModelFactory
import com.moes_code.moes_gym_app.ui.viewmodel.WorkoutPlanListViewModel
import com.moes_code.moes_gym_app.ui.viewmodel.WorkoutSessionViewModel

@Composable
fun GymNavHost(repository: GymRepository) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "plans") {
        composable("plans") {
            val factory = GymViewModelFactory(repository)
            val viewModel = viewModel<WorkoutPlanListViewModel>(factory = factory)
            val plans by viewModel.plans.collectAsState()

            PlanListScreen(
                plans = plans,
                onPlanClick = { planId -> navController.navigate("session/$planId") },
                onEditPlan = { planId -> navController.navigate("editPlan/$planId") },
                onCreatePlan = { name, desc -> viewModel.createPlan(name, desc) },
                onDeletePlan = { plan -> viewModel.deletePlan(plan) }
            )
        }

        composable(
            route = "session/{planId}",
            arguments = listOf(navArgument("planId") { type = NavType.LongType })
        ) { backStackEntry ->
            val planId = backStackEntry.arguments?.getLong("planId") ?: return@composable
            val factory = GymViewModelFactory(repository, planId)
            val viewModel = viewModel<WorkoutSessionViewModel>(factory = factory)

            SessionScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "editPlan/{planId}",
            arguments = listOf(navArgument("planId") { type = NavType.LongType })
        ) { backStackEntry ->
            val planId = backStackEntry.arguments?.getLong("planId") ?: return@composable
            val factory = GymViewModelFactory(repository, planId)
            val viewModel = viewModel<EditPlanViewModel>(factory = factory)

            EditPlanScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
