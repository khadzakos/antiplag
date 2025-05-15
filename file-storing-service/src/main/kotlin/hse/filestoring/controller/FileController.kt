package hse.filestoring.controller

import hse.filestoring.service.FileStorageService
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/files")
class FileController(
    private val fileStorageService: FileStorageService
) {
    @GetMapping("/")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("File Storing Service is running")
    }

    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        val filename = fileStorageService.store(file)
        return ResponseEntity.ok("Uploaded: $filename")
    }

    @GetMapping("/{id}", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun download(@PathVariable id: ULong): ResponseEntity<String> {
        val resource = fileStorageService.load(id)

        val content = resource.inputStream.bufferedReader().use { it.readText() }

        return ResponseEntity.ok()
            .contentType(MediaType.TEXT_PLAIN)
            .body(content)
    }
}