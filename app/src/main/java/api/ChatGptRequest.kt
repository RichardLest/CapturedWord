package api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Paso 1: Define una clase modelo para la solicitud
data class ChatGptRequest(
    val prompt: String,
    val maxTokens: Int
) {
    // Método para enviar la solicitud a la API de ChatGPT
    fun enviarSolicitud(palabra: String, callback: (CompletionResponse?, String?) -> Unit) {
        try {
            val service = ApiClient.create()
            val call = service.generateText(prompt = palabra, maxTokens = 100)

            call.enqueue(object : Callback<CompletionResponse> {
                override fun onResponse(call: Call<CompletionResponse>, response: Response<CompletionResponse>) {
                    // Código de respuesta exitosa
                }

                override fun onFailure(call: Call<CompletionResponse>, t: Throwable) {
                    // Manejar errores de red y tiempo de espera
                    val errorMessage = "Error de conexión: ${t.message}"
                    callback(null, errorMessage)
                }
            })
        } catch (e: Exception) {
            // Manejar otros errores
            val errorMessage = "Error desconocido: ${e.message}"
            callback(null, errorMessage)
        }
    }
}