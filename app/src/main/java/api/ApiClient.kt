package api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://api.openai.com/v1/" // URL base de la API de ChatGPT

    // Función para obtener una instancia de ChatGptApiService
    fun create(): ChatGptApiService {
        // Configurar Retrofit con la URL base y el convertidor Gson
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear e inicializar la interfaz ChatGptApiService
        return retrofit.create(ChatGptApiService::class.java)
    }
}