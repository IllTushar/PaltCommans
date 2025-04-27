package com.example.test.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserResponseRoomDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): AppDao
}