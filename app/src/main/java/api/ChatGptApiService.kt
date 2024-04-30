package api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ChatGptApiService {
    // Define el endpoint para enviar solicitudes de generación de texto
    @GET("chat/completions/v1")
    // Define los encabezados necesarios, incluyendo tu clave de API
    @Headers(
        "Authorization: Bearer sk-proj-iL7fb5FXBGB40QyeG9BtT3BlbkFJP3RXXL6trLLQY0l3aXBT",
        "Content-Type: application/json"
    )
    // Define el método para enviar una solicitud de generación de texto
    fun generateText(
        // Define los parámetros necesarios, como el texto de entrada y el número máximo de tokens
        @Query("prompt") prompt: String,
        @Query("max_tokens") maxTokens: Int
    ): Call<CompletionResponse> // Asegúrate de que el tipo de retorno sea CompletionResponse
}