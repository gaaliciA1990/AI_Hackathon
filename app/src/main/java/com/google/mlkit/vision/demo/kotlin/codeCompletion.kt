package com.google.mlkit.vision.demo.kotlin

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException


fun main() {
    val apiKey = System.getenv("OPEN_API_KEY")
    println("API key: $apiKey")

    if (apiKey == null || apiKey.isEmpty()) {
        throw IllegalArgumentException("API key must be set in the OPEN_API_KEY environment variable.")
    }

    val prompt = getUserInput()
    val completedChat = completeChat(apiKey, prompt)
    println(completedChat)
}

fun getUserInput(): String {
    print("Enter your prompt: ")
    return readLine() ?: ""
}

@Serializable
data class CompletionResponse(
    val choices: List<CompletionChoice>
)

@Serializable
data class CompletionChoice(
    val text: String
)

fun completeChat(apiKey: String, prompt: String): String {
    val url = "https://api.openai.com/v1/completions"
    val requestBody = """
        {
            "prompt": "$prompt",
            "temperature": 0.7,
            "max_tokens": 50,
            "n": 1,
            "stop": ".",
            "model": "text-davinci-002"
        }
    """.trimIndent().toRequestBody("application/json".toMediaType())
    val request = Request.Builder()
        .url(url)
        .header("Authorization", "Bearer $apiKey")
        .post(requestBody)
        .build()
    val client = OkHttpClient()
    val response: Response = client.newCall(request).execute()
    if (!response.isSuccessful) {
        throw IOException("Unexpected code $response")
    }
    val responseBodyString = response.body?.string() ?: ""
    val json = Json { ignoreUnknownKeys = true }
    val completionResponse = json.decodeFromString<CompletionResponse>(responseBodyString)
    return completionResponse.choices.first().text
}
