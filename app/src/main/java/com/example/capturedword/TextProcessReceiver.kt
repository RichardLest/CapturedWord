package com.example.capturedword

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.capturedword.database.Word
import com.example.capturedword.database.WordDatabase

class TextProcessReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_PROCESS_TEXT == intent.action) {
            val selectedText = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
            if (selectedText != null) {
                // Aquí puedes realizar acciones con el texto seleccionado, como guardarlo en la base de datos
                saveTextToDatabase(context, selectedText.toString())
            }
        }
    }
    private fun saveTextToDatabase(context: Context, text: String) {
        // Llamar a la función insert dentro de un bloque launch de corutina
        GlobalScope.launch(Dispatchers.IO) {
            val wordDatabase = WordDatabase.getInstance(context)
            wordDatabase.wordDao().insert(Word(word = text))
        }
    }
}