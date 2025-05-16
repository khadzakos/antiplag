package hse.fileanalysis.controller

import hse.fileanalysis.service.FileAnalysisService
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/analyze")
class AnalysisConroller (
    private val fileAnalysisService: FileAnalysisService
) {
    @GetMapping("/")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("File Analysis Service is running")
    }

    @PostMapping("/{id}")
    fun analyze(@PathVariable id: ULong): ResponseEntity<String> {
        val result = fileAnalysisService.analyze(id)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/cloud/{location}", produces = [MediaType.IMAGE_PNG_VALUE])
    fun download(@PathVariable location: String): ResponseEntity<Resource> {
        val resource = fileAnalysisService.download(location)
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(resource)
    }
}