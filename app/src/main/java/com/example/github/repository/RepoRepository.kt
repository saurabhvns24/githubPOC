package com.example.github.repository

import com.example.github.model.Contributor
import com.example.github.model.Repo
import com.example.github.network.GitHubApiService
import com.example.github.room.RepoDao
import com.example.github.room.RepoEntity

class RepoRepository(private val apiService: GitHubApiService, private val repoDao: RepoDao) {
    suspend fun searchRepos(query: String, page: Int): List<Repo> {
        val response = apiService.searchRepos(query,10, page)
        val repoEntities = response.items.map { it.toRepoEntity() }
        saveReposOffline(repoEntities)
        return response.items
//        return apiService.searchRepos(query, 10, page).items
    }

    private suspend fun saveReposOffline(repos: List<RepoEntity>) {
        repoDao.insertRepos(repos)
    }

    suspend fun getReposOffline(): List<RepoEntity> {
        return repoDao.getReposList()
    }

    suspend fun getContributors(contributorsUrl: String): List<Contributor> {
        return apiService.getContributors(contributorsUrl)
    }

}
