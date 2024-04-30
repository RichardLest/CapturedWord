package data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "captured_word.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableSQL = "CREATE TABLE IF NOT EXISTS captured_words ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "word TEXT, " +
                "translation TEXT, " +
                "ipa TEXT, " +
                "example_en TEXT, " +
                "example_es TEXT" +
                ")"
        db.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Este método se llama automáticamente cuando se detecta que la versión de la base de datos ha cambiado.
        // Aquí puedes implementar la lógica para actualizar la estructura de la base de datos.
        // Por ejemplo, puedes eliminar la tabla existente y crear una nueva tabla con una estructura diferente.
        db.execSQL("DROP TABLE IF EXISTS captured_words")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Este método se llama automáticamente cuando se detecta que la versión de la base de datos ha disminuido.
        // Aquí puedes implementar la lógica para manejar la degradación de la versión de la base de datos, si es necesario.
        // En este ejemplo, simplemente eliminamos la tabla existente y la volvemos a crear.
        db.execSQL("DROP TABLE IF EXISTS captured_words")
        onCreate(db)
    }

    // Método para insertar una palabra capturada en la base de datos
    fun insertCapturedWord(word: String, translation: String, ipa: String, exampleEn: String, exampleEs: String): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("word", word)
            put("translation", translation)
            put("ipa", ipa)
            put("example_en", exampleEn)
            put("example_es", exampleEs)
        }
        return db.insert("captured_words", null, contentValues)
    }

    // Método para recuperar todas las palabras capturadas de la base de datos
    fun getAllCapturedWords(): Cursor? {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM captured_words", null)
    }

    // Otros métodos CRUD (Actualizar, Eliminar) pueden ser implementados de manera similar según tus necesidades
}