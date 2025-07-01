package com.mayank.wifisync.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Int,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val avatar: String?,
    val fetchedAt: Long
)

