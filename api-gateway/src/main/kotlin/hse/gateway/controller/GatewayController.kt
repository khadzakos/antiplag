package hse.gateway.controller

import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

import hse.gateway.util.MultipartInputStreamFileResource

@RestController
@RequestMapping("/gateway")
class GatewayController(
    private val restTemplate: RestTemplate
) {

    private val fileService = "http://localhost:8081"
    private val analysisService = "http://localhost:8082"

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

    @PostMapping("/analyze/{id}")
    fun analyze(@PathVariable id: Long): ResponseEntity<String> {
        val response = restTemplate.postForEntity("$analysisService/analyze/$id", null, String::class.java)
        return ResponseEntity.status(response.statusCode).body(response.body)
    }

    @GetMapping("/cloud/{filename}")
    fun getCloud(@PathVariable filename: String): ResponseEntity<ByteArray> {
        val url = "$analysisService/analyze/cloud/$filename"
        val response = restTemplate.getForEntity(url, ByteArray::class.java)
        return ResponseEntity.status(response.statusCode)
            .contentType(MediaType.IMAGE_PNG)
            .body(response.body)
    }
}