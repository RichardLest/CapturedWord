package api

import com.google.gson.annotations.SerializedName

data class CompletionResponse(
    @SerializedName("generated_text") val generatedText: String,
    // Aquí puedes agregar más propiedades según la estructura de la respuesta de la API
)