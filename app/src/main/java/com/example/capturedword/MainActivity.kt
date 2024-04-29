package com.example.capturedword

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capturedword.database.WordDatabase
import androidx.room.Room
import com.example.capturedword.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.capturedword.database.Word // Importa la clase Word
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var wordDatabase: WordDatabase // Declarar la variable wordDatabase

    companion object {
        const val EXTRA_TEXT_TO_SAVE = "extra_text_to_save"
    }

    fun MainActivity.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar la base de datos
        wordDatabase = Room.databaseBuilder(
            applicationContext,
            WordDatabase::class.java, "word_database"
        ).build()

        binding.saveButton.setOnClickListener {
            val enteredText = binding.editText.text.toString().trim()
            if (enteredText.isNotEmpty()) {
                GlobalScope.launch(Dispatchers.IO) {
                    wordDatabase.wordDao().insert(Word(word = enteredText))
                }
                showToast("Texto guardado exitosamente")
            } else {
                showToast("Por favor ingresa un texto antes de guardar")
            }
        }
    }
}
