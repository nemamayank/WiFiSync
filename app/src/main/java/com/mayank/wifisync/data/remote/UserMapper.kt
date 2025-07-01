package com.mayank.wifisync.data.remote

import com.mayank.wifisync.data.local.User

fun UserDto.toUser(fetchedAt: Long): User? {

    if (email.isNullOrBlank() || avatar.isNullOrBlank()) return null

    return User(
        id = id,
        email = email,
        firstName = first_name,
        lastName = last_name,
        avatar = avatar,
        fetchedAt = fetchedAt
    )
}