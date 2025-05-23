package hse.fileanalysis.controller

import hse.fileanalysis.service.FileAnalysisService
import hse.fileanalysis.controller.dto.ErrorResponse
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@RestController
@RequestMapping("/analyze")
class AnalysisConroller (
    private val fileAnalysisService: FileAnalysisService
) {

    @Operation(
        summary = "Health check",
        responses = [
            ApiResponse(responseCode = "200", description = "Сервис работает"),
            ApiResponse(responseCode = "500", description = "Сервис не работает")
        ]
    )
    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("File Analysis Service is running")
    }

    @Operation(
        summary = "Анализирует файл",
        requestBody = RequestBody(
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ULong::class)
            )]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Файл успешно проанализирован"),
            ApiResponse(responseCode = "400", description = "Ошибка при анализе файла"),
            ApiResponse(
                responseCode = "500",
                description = "Ошибка сервера"
            )
        ]
    )
    @PostMapping("/{id}")
    fun analyze(@PathVariable id: ULong): ResponseEntity<Any> {
        try {
            val result = fileAnalysisService.analyze(id)
            return ResponseEntity.ok(result)
        } catch (e: Exception) {
            val errorResponse = ErrorResponse(
                timestamp = java.time.Instant.now().toString(),
                status = HttpStatus.BAD_REQUEST.value(),
                error = HttpStatus.BAD_REQUEST.reasonPhrase,
                message = "File analysis failed: check if the file exists"
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse)
        }
    }

    @Operation(
        summary = "Получает облако слов",
        parameters = [
            Parameter(
                name = "location",
                description = "Имя файла",
                required = true,
                schema = Schema(type = "string")
            )
        ],
        responses = [
            ApiResponse(responseCode = "200", description = "Файл успешно получен"),
            ApiResponse(responseCode = "400", description = "Ошибка при получении файла"),
            ApiResponse(
                responseCode = "500",
                description = "Ошибка сервера"
            )
        ]
    )
    @GetMapping("/cloud/{location}", produces = [MediaType.IMAGE_PNG_VALUE])
    fun download(@PathVariable location: String): ResponseEntity<Resource> {
        val resource = fileAnalysisService.download(location)
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(resource)
    }
}