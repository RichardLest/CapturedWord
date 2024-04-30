package com.example.capturedword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.capturedword.database.Word;
import com.example.capturedword.database.WordDao;
import com.example.capturedword.database.WordDatabase;

public class TextSelectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtener el texto seleccionado de la intención
        CharSequence selectedText = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);

        // Verificar si se seleccionó algún texto
        if (selectedText != null) {
            // Aquí puedes guardar el texto seleccionado en tu base de datos
            saveSelectedText(selectedText.toString());
        } else {
            // Si no se seleccionó ningún texto, mostrar un mensaje de error
            Toast.makeText(this, "No se seleccionó ningún texto", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para guardar el texto seleccionado en la base de datos
    private void saveSelectedText(String text) {
        // Obtener una instancia de la base de datos
        WordDatabase wordDatabase = WordDatabase.Companion.getInstance(this);

        // Obtener el DAO asociado a la tabla de palabras
        WordDao wordDao = wordDatabase.wordDao();

        // Crear un objeto Word con el texto seleccionado
        Word word = new Word(text, null, null, null, null);

        // Insertar la palabra en la base de datos
        wordDao.insert(word);

        // Mostrar un mensaje de tostada en el hilo principal
        Toast.makeText(this, "Texto guardado: " + text, Toast.LENGTH_SHORT).show();
    }
}