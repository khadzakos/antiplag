package hse.gateway.controller

import hse.gateway.config.ServiceUrls
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

import hse.gateway.util.MultipartInputStreamFileResource

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag


@RestController
@RequestMapping("/gateway")
class GatewayController(
    private val restTemplate: RestTemplate,
    private val serviceUrls: ServiceUrls
) {
    private val fileService = serviceUrls.fileServiceUrl
    private val analysisService = serviceUrls.analysisServiceUrl
    private val storageService = serviceUrls.fileServiceUrl

    @Operation(summary = "Загружает файл на сервер",
        responses = [
            ApiResponse(responseCode = "200", description = "Файл успешно загружен"),
            ApiResponse(responseCode = "400", description = "Ошибка при загрузке файла"),
            ApiResponse(
                responseCode = "500",
                description = "Ошибка сервера"
            )
        ]
    )
    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(@RequestPart("file") file: MultipartFile): ResponseEntity<String> {
        val headers = HttpHeaders().apply {
            contentType = MediaType.MULTIPART_FORM_DATA
        }

        val body = LinkedMultiValueMap<String, Any>().apply {
            add("file", MultipartInputStreamFileResource(file))
        }

        val request = HttpEntity(body, headers)
        val response = restTemplate.postForEntity("$fileService/files/upload", request, String::class.java)
        return ResponseEntity.status(response.statusCode).body(response.body)
    }

    @Operation(summary = "Получает текст файла",
        parameters = [
            Parameter(name = "id", description = "ID файла", required = true)
        ],
        responses = [
            ApiResponse(responseCode = "200", description = "Текст файла успешно получен"),
            ApiResponse(responseCode = "400", description = "Ошибка при получении текста файла"),
            ApiResponse(
                responseCode = "500",
                description = "Ошибка сервера"
            )
        ]
    )
    @GetMapping("/{id}")
    fun getFileText(@PathVariable id: Long): ResponseEntity<String> {
        val response = restTemplate.getForEntity("$fileService/files//text/$id", String::class.java)
        return ResponseEntity.status(response.statusCode).body(response.body)
    }

    @Operation(summary = "Анализирует файл",
        parameters = [
            Parameter(name = "id", description = "ID файла", required = true)
        ],
        responses = [
            ApiResponse(responseCode = "200", description = "Файл успешно проанализирован"),
            ApiResponse(responseCode = "400", description = "Ошибка при анализе файла"),
            ApiResponse(
                responseCode = "500",
                description = "Ошибка сервера"
            )
        ]
    )
    @PostMapping("/analyze/{id}")
    fun analyze(@PathVariable id: Long): ResponseEntity<String> {
        val response = restTemplate.postForEntity("$analysisService/analyze/$id", null, String::class.java)
        return ResponseEntity.status(response.statusCode).body(response.body)
    }

    @Operation(summary = "Получает облако слов",
        parameters = [
            Parameter(name = "filename", description = "Имя файла", required = true)
        ],
        responses = [
            ApiResponse(responseCode = "200", description = "Облако слов успешно получено"),
            ApiResponse(responseCode = "400", description = "Ошибка при получении облака слов"),
            ApiResponse(
                responseCode = "500",
                description = "Ошибка сервера"
            )
        ]
    )
    @GetMapping("/cloud/{filename}")
    fun getCloud(@PathVariable filename: String): ResponseEntity<ByteArray> {
        val url = "$analysisService/analyze/cloud/$filename"
        val response = restTemplate.getForEntity(url, ByteArray::class.java)
        return ResponseEntity.status(response.statusCode)
            .contentType(MediaType.IMAGE_PNG)
            .body(response.body)
    }
}