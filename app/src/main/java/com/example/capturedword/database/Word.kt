package com.example.capturedword.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(
    val word: String,
    val translation: String? = null,
    val ipa: String? = null,
    val exampleEn: String? = null,
    val exampleEs: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    fun someFunction() {
        // Código de la función aquí
    }
}