package com.example.github.network

import com.example.github.model.Contributor
import com.example.github.model.RepoResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GitHubApiService {
    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): RepoResponse

    @GET
    suspend fun getContributors(@Url contributorsUrl: String): List<Contributor>
}
