package com.example.github.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.model.Contributor
import com.example.github.model.Repo
import com.example.github.repository.RepoRepository
import kotlinx.coroutines.launch

class RepoViewModel(private val repository: RepoRepository) : ViewModel() {

    val repos = MutableLiveData<List<Repo>>()
    val contributors = MutableLiveData<List<Contributor>>()

    fun searchRepos(query: String, page: Int) {
        viewModelScope.launch {
            val result = repository.searchRepos(query, page)
            repos.postValue(result)
        }
    }

    fun loadContributors(contributorsUrl: String) {
        viewModelScope.launch {
            val result = repository.getContributors(contributorsUrl)
            contributors.postValue(result)
        }
    }

    fun loadOfflineRepos() {
        viewModelScope.launch {
            // Load offline repositories when needed
            val offlineRepos = repository.getReposOffline()
            repos.postValue(offlineRepos.map { it.toRepo() }) // Assuming you have a method to convert RepoEntity back to Repo
        }
    }
}
