package com.example.github

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.room.Room
import com.example.github.network.RetrofitInstance
import com.example.github.repository.RepoRepository
import com.example.github.room.RepoDatabase
import com.example.github.screenNavigation.AppNavigation
import com.example.github.viewmodel.RepoViewModel
import com.example.github.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            RepoDatabase::class.java, "repo-database"
        ).build()
    }

    private val apiService by lazy {
        RetrofitInstance.api
    }

    private val repoRepository by lazy {
        RepoRepository(apiService, database.repoDao())
    }

    private val viewModel: RepoViewModel by viewModels {
        ViewModelFactory(repoRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation(viewModel = viewModel)
        }
    }
}
