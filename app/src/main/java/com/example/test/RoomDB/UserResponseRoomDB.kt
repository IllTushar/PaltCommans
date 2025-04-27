package com.example.test.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserResponseRoomDB(
    val name: String,
    val job: String,
    @PrimaryKey
    val id: String,
    val createdAt: String,
)
