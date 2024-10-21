package com.example.github.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<RepoEntity>)

    @Query("SELECT * FROM repos")
    fun getRepos(): LiveData<List<RepoEntity>>

    // New method to get repos as a list
    @Query("SELECT * FROM repos")
    suspend fun getReposList(): List<RepoEntity>
}
