package com.example.github.screenNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.github.screens.HomeScreen
import com.example.github.screens.RepoDetailScreen
import com.example.github.viewmodel.RepoViewModel

@Composable
fun AppNavigation(viewModel: RepoViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(viewModel = viewModel) { repo ->
                navController.navigate("details/${repo.id}")
            }
        }
        composable("details/{repoId}") { backStackEntry ->
            val repoId = backStackEntry.arguments?.getString("repoId")?.toIntOrNull()
            repoId?.let {
                val repo = viewModel.repos.value?.find { it.id == repoId }
                repo?.let { RepoDetailScreen(it, viewModel) }
            }
        }
    }
}
