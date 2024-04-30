package com.example.capturedword

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.capturedword.database.WordDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.widget.EditText
import com.example.capturedword.database.Word
import android.widget.Button
import android.widget.Toast
import api.CompletionResponse
import api.ChatGptRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var wordDatabase: WordDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordDatabase = WordDatabase.getInstance(this)

        mostrarPalabrasGuardadas()

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            guardarPalabra()
        }
    }

    private fun guardarPalabra() {
        val enteredText = findViewById<EditText>(R.id.editText).text.toString().trim()
        if (enteredText.isNotEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                wordDatabase.wordDao().insert(Word(word = enteredText))
                mostrarPalabrasGuardadas() // Mostrar las palabras guardadas después de insertar una nueva
            }
            showToast("Palabra guardada exitosamente")
            // Limpiar el EditText después de guardar la palabra
            findViewById<EditText>(R.id.editText).text.clear()
        } else {
            showToast("Por favor ingresa una palabra antes de guardar")
        }
    }

    private fun mostrarPalabrasGuardadas() {
        val textView = findViewById<TextView>(R.id.textView)

        GlobalScope.launch(Dispatchers.IO) {
            val cursor = wordDatabase.wordDao().getAllWordsCursor()

            val palabrasList = mutableListOf<Word>() // Lista para almacenar las palabras

            // Verificar si el cursor no es nulo y contiene al menos una fila
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Extraer los datos relevantes de cada fila del cursor
                    val palabra = cursor.getString(cursor.getColumnIndexOrThrow("word"))

                    // Crear un objeto Word con la palabra recuperada
                    val word = Word(word = palabra)
                    // Agregar el objeto Word a la lista de palabras
                    palabrasList.add(word)
                } while (cursor.moveToNext())

                // Cerrar el cursor después de iterar sobre él
                cursor.close()

                // Actualizar el TextView en el hilo principal
                runOnUiThread {
                    // Iterar sobre la lista de palabras y enviar solicitudes a la API de ChatGPT
                    for (word in palabrasList) {
                        enviarSolicitudAPI(word)
                    }
                }
            }
        }
    }

    private fun updateUIWithGeneratedText(generatedText: String) {
        // Aquí puedes actualizar la interfaz de usuario con el texto generado
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = generatedText
    }

    private fun enviarSolicitudAPI(palabra: Word) {
        // Aquí debes escribir el código para enviar una solicitud a la API de ChatGPT
        // Utiliza la palabra recuperada como prompt en la solicitud
        // Procesa la respuesta de la API y muestra la información adicional en la interfaz de usuario
        // Este es solo un ejemplo de cómo podrías hacerlo
        val informacionGenerada = "Información generada para la palabra: $palabra"
        mostrarInformacionGenerada(informacionGenerada)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Método para manejar errores de red
    private fun handleNetworkError(message: String) {
        showToast("Error de red: $message")
    }

    private fun mostrarInformacionGenerada(informacion: String) {
        // Aquí puedes mostrar la información generada por la API en tu interfaz de usuario
        // Puedes usar un TextView u otro componente de la interfaz de usuario para mostrar la información
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = informacion
    }

    fun updateUIWithCompletion(completionResponse: CompletionResponse) {
        // Actualizar la interfaz de usuario con la información recibida de la API
    }

    private fun enviarSolicitudAChatGpt(palabra: String) {
        // Crear una instancia de ChatGptRequest
        val chatGptRequest = ChatGptRequest(prompt = palabra, maxTokens = 100)

        // Llamar al método para enviar la solicitud con la palabra como parámetro
        chatGptRequest.enviarSolicitud(palabra) { completionResponse, errorMessage ->
            // Manejar la respuesta de la API aquí
            if (completionResponse != null) {
                // La solicitud fue exitosa, procesar la respuesta
                // Por ejemplo, puedes actualizar la interfaz de usuario con la respuesta
                updateUIWithCompletion(completionResponse)
            } else {
                // Ocurrió un error al enviar la solicitud
                showToast(errorMessage ?: "Error al obtener la respuesta de la API")
            }
        }
    }
}
