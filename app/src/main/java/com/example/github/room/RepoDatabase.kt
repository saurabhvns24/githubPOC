package com.example.github.room

import androidx.room.Database
import androidx.room.RoomDatabase

// Entities for Room
@Database(entities = [RepoEntity::class], version = 1)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}
