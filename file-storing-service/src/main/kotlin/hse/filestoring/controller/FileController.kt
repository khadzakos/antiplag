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

    // Uploads a file and returns its id
    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        val file_id = fileStorageService.store(file)
        return ResponseEntity.ok("Uploaded file, ID: $file_id")
    }

    // Returns text of the file by file id
    @GetMapping("/text/{id}", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun download(@PathVariable id: ULong): ResponseEntity<String> {
        val resource = fileStorageService.load(id)

        val content = resource.inputStream.bufferedReader().use { it.readText() }

        return ResponseEntity.ok()
            .contentType(MediaType.TEXT_PLAIN)
            .body(content)
    }

    // Returns file metadata by file id
    @GetMapping("/info/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun fileInfo(@PathVariable id: ULong): ResponseEntity<String> {
        val fileMeta = fileStorageService.fileInfo(id)
        return ResponseEntity.ok(fileMeta.toString())
    }
}