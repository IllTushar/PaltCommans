package com.example.test.RoomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserResponseRoomDB)

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<UserResponseRoomDB>>
}