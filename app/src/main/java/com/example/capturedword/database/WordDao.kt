package com.example.capturedword.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import android.database.Cursor

@Dao
interface WordDao {
    @Insert
    fun insert(word: Word)

    @Update
    suspend fun update(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Query("SELECT * FROM word_table")
    suspend fun getAllWords(): List<Word>

    @Query("SELECT * FROM word_table")
    fun getAllWordsCursor(): Cursor?
}