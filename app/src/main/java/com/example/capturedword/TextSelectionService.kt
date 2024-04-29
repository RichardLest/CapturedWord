import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.capturedword.database.Word
import com.example.capturedword.database.WordDao

class TextSelectionService : Service() {

    private lateinit var wordDao: WordDao

    fun setWordDao(wordDao: WordDao) {
        this.wordDao = wordDao
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: Service started")
        // Obtener la acción de la intención
        val action = intent?.action

        // Verificar si la acción es la de procesar texto
        if (action == Intent.ACTION_PROCESS_TEXT) {
            // Obtener el texto seleccionado de la intención
            val selectedText = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)

            // Verificar si se seleccionó algún texto
            selectedText?.let { text ->
                // Mostrar un mensaje de confirmación al usuario
                Toast.makeText(this, "Texto seleccionado: $text", Toast.LENGTH_SHORT).show()

                // Aquí puedes guardar el texto seleccionado en tu base de datos
                saveSelectedText(text.toString())
            }
        }

        // No detener el servicio automáticamente después de ejecutar onStartCommand()
        return START_NOT_STICKY
    }

    private fun saveSelectedText(text: String) {
        // Verificar si se ha inicializado el DAO
        if (!::wordDao.isInitialized) {
            // Mostrar un mensaje de error si el DAO no está inicializado
            Log.e(TAG, "saveSelectedText: DAO not initialized")
            Toast.makeText(this, "Error: DAO no inicializado", Toast.LENGTH_SHORT).show()
            return
        }

        // Insertar el texto seleccionado en la base de datos
        val word = Word(text)
        wordDao.insert(word)

        // Mostrar un mensaje de confirmación al usuario
        Toast.makeText(this, "Texto guardado: $text", Toast.LENGTH_SHORT).show()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        private const val TAG = "TextSelectionService"
    }
}