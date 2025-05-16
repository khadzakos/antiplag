package hse.gateway.util

import org.springframework.core.io.InputStreamResource
import org.springframework.web.multipart.MultipartFile

class MultipartInputStreamFileResource(private val file: MultipartFile) :
    InputStreamResource(file.inputStream) {

    override fun getFilename(): String = file.originalFilename ?: "file"

    override fun contentLength(): Long = file.size
}