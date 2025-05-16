package hse.fileanalysis.client

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class WordCloudClient(private val restTemplate: RestTemplate) {

    fun generateCloud(text: String): ByteArray {
        val url = "https://quickchart.io/wordcloud"
        val request = mapOf("format" to "png", "text" to text)

        return restTemplate.postForObject(url, request, ByteArray::class.java)
            ?: throw RuntimeException("Failed to get word cloud")
    }
}