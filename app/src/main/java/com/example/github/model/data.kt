package com.example.github.model

import com.example.github.room.RepoEntity

data class Repo(
    val id: Int,
    val name: String,
    val description: String?,
    val owner: Owner,
    val html_url: String,
    val contributors_url: String
) {
    fun toRepoEntity():RepoEntity {
        return RepoEntity(
            id = this.id,
            name = this.name,
            description = this.description,
            htmlUrl = this.html_url,
            ownerAvatarUrl = this.owner.avatar_url,
            ownerName = this.owner.login,
            contributors_url = this.contributors_url
        )
    }
}

data class Owner(
    val avatar_url: String,
    val login: String
)

data class Contributor(
    val login: String,
    val avatar_url: String
)

data class RepoResponse(
    val total_count: Int,          // Total number of repositories found
    val items: List<Repo>          // List of repositories (mapped to your Repo data class)
)
