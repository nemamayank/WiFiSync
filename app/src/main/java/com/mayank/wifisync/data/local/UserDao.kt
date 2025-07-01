package com.mayank.wifisync.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM User Where id = :id")
    fun getDetails(id: Int): Flow<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Query("DELETE FROM User")
    suspend fun deleteAll()

    @Transaction
    suspend fun updateAll(users: List<User>) {
        deleteAll()
        insertAll(users)
    }
}