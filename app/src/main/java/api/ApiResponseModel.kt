package api

import com.google.gson.annotations.SerializedName

data class ApiResponseModel(
    @SerializedName("choices") val choices: List<ChoiceModel>
)

data class ChoiceModel(
    @SerializedName("text") val text: String,
    @SerializedName("index") val index: Int,
    @SerializedName("logprobs") val logprobs: LogprobsModel,
    @SerializedName("finish_reason") val finishReason: String
)

data class LogprobsModel(
    @SerializedName("token_logprobs") val tokenLogprobs: List<Double>,
    @SerializedName("top_logprobs") val topLogprobs: List<Map<String, Double>>,
    @SerializedName("text_offset") val textOffset: Int
)