package hse.filestoring.controller

import hse.filestoring.service.FileStorageService
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@RestController
@RequestMapping("/files")
class FileController(
    private val fileStorageService: FileStorageService
) {

    @Operation(
        summary = "Health check",
        responses = [
            ApiResponse(responseCode = "200", description = "Сервис работает"),
            ApiResponse(responseCode = "500", description = "Сервис не работает")
        ]
    )
    @GetMapping("/")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("File Storing Service is running")
    }

    @Operation(
        summary = "Загружает файл на сервер",
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
    fun upload(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        val file_id = fileStorageService.store(file)
        if (file_id == ULong.MAX_VALUE) {
            return ResponseEntity.status(400).body("File already exists")
        }
        return ResponseEntity.ok("Uploaded file, ID: $file_id")
    }

    @Operation(
        summary = "Получает текст файла",
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
    @GetMapping("/text/{id}", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun download(@PathVariable id: ULong): ResponseEntity<String> {
        val resource = fileStorageService.load(id)

        val content = resource.inputStream.bufferedReader().use { it.readText() }

        return ResponseEntity.ok()
            .contentType(MediaType.TEXT_PLAIN)
            .body(content)
    }

    @Operation(
        summary = "Получает метаданные файла",
        parameters = [
            Parameter(name = "id", description = "ID файла", required = true)
        ],
        responses = [
            ApiResponse(responseCode = "200", description = "Метаданные файла успешно получены"),
            ApiResponse(responseCode = "400", description = "Ошибка при получении метаданных файла"),
            ApiResponse(
                responseCode = "500",
                description = "Ошибка сервера"
            )
        ]
    )
    @GetMapping("/info/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun fileInfo(@PathVariable id: ULong): ResponseEntity<String> {
        val fileMeta = fileStorageService.fileInfo(id)
        return ResponseEntity.ok(fileMeta.toString())
    }
}