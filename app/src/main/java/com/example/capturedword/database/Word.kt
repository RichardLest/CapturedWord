package com.example.capturedword.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(
    val word: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}