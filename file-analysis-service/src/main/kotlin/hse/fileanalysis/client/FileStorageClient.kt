package hse.fileanalysis.client

import hse.fileanalysis.client.dto.FileMetaDto
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class FileStorageClient(private val restTemplate: RestTemplate) {

    fun loadText(fileId: ULong): String {
        val textUrl = "http://localhost:8081/files/text/$fileId"
        return restTemplate.getForObject(textUrl, String::class.java)
            ?: throw RuntimeException("Failed to get file")
    }

    fun loadInfo(fileId: ULong): FileMetaDto {
        val metaUrl = "http://localhost:8081/files/info/$fileId"

        return restTemplate.getForObject(metaUrl, FileMetaDto::class.java)
            ?: throw RuntimeException("Failed to get file")
    }
}