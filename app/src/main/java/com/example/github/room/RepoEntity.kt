package com.example.github.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.github.model.Owner
import com.example.github.model.Repo

@Entity(tableName = "repos")
data class RepoEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String?,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val htmlUrl: String,
    val contributors_url:String
) {
    fun toRepo(): Repo {
        return Repo(
            id = this.id,
            name = this.name,
            description = this.description,
            html_url = this.htmlUrl,
            owner = Owner(avatar_url = this.ownerAvatarUrl, login = this.ownerName),
            contributors_url = this.contributors_url
        )
    }
}
